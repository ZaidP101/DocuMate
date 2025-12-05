import { app, BrowserWindow, Menu } from 'electron'
import { spawn } from 'child_process'
import path from 'path'
import { fileURLToPath } from 'url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)

const isDev = process.env.NODE_ENV === 'development'
let backendProcess = null
let mainWindow = null

function startBackendServer() {
    if (isDev) {
        console.log('Dev mode: Assuming backend is running on port 8181')
        return
    }

    // In production: start backend from packaged JAR
    const resourcesPath = process.resourcesPath
    const backendJar = path.join(resourcesPath, 'backend.jar')
    
    console.log('Starting backend from:', backendJar)

    backendProcess = spawn('java', ['-jar', backendJar], {
        stdio: 'pipe',
        detached: false
    })

    backendProcess.stdout.on('data', (data) => {
        console.log(`Backend: ${data}`)
    })

    backendProcess.stderr.on('data', (data) => {
        console.error(`Backend Error: ${data}`)
    })

    backendProcess.on('error', (err) => {
        console.error('Failed to start backend:', err)
    })

    backendProcess.on('exit', (code) => {
        console.log(`Backend process exited with code ${code}`)
    })
}

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 1400,
        height: 900,
        icon: path.join(__dirname, '../public/DocuMate.ico'),
        webPreferences: {
            preload: path.join(__dirname, 'preload.js'),
            nodeIntegration: false,
            contextIsolation: true,
        },
        show: false // Don't show until ready
    })

    // Remove default menu for cleaner look
    Menu.setApplicationMenu(null)

    if (isDev) {
        mainWindow.loadURL('http://localhost:5173')
        mainWindow.webContents.openDevTools()
    } else {
        mainWindow.loadFile(path.join(__dirname, '../dist/index.html'))
    }

    // Show window when content is loaded
    mainWindow.once('ready-to-show', () => {
        mainWindow.show()
    })

    mainWindow.on('closed', () => {
        mainWindow = null
    })
}

app.whenReady().then(() => {
    console.log('🚀 DocuMate starting...')
    
    // 1. Start backend server first
    startBackendServer()
    
    // 2. Wait for backend to start, then create window
    setTimeout(() => {
        createWindow()
    }, isDev ? 1000 : 3000) // Give backend time to start
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        // Kill backend process when app closes
        if (backendProcess) {
            backendProcess.kill()
        }
        app.quit()
    }
})

app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
        createWindow()
    }
})

app.on('before-quit', () => {
    // Ensure backend is killed when app quits
    if (backendProcess) {
        backendProcess.kill()
    }
})