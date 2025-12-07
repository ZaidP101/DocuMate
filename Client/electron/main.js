const { app, BrowserWindow, Menu } = require('electron');
const { spawn } = require('child_process');
const path = require('path');
const fs = require('fs');

const isDev = process.env.NODE_ENV === 'development';
let backendProcess = null;
let mainWindow = null;

function startBackendServer() {
    if (isDev) {
        console.log('Dev mode: Assuming backend is running on port 8181');
        return;
    }

    // In production: start backend from packaged JAR
    const resourcesPath = process.resourcesPath;
    const backendJar = path.join(resourcesPath, 'backend.jar');
    
    console.log('Looking for backend JAR at:', backendJar);
    
    // Check if file exists
    if (!fs.existsSync(backendJar)) {
        console.error('Backend JAR not found! Checking resources:');
        const files = fs.readdirSync(resourcesPath);
        console.log('Resources files:', files);
        return;
    }
    
    console.log('Starting backend from:', backendJar);

    backendProcess = spawn('java', ['-jar', backendJar], {
        stdio: 'pipe',
        detached: false
    });

    backendProcess.stdout.on('data', (data) => {
        console.log(`Backend: ${data}`);
    });

    backendProcess.stderr.on('data', (data) => {
        console.error(`Backend Error: ${data}`);
    });

    backendProcess.on('error', (err) => {
        console.error('Failed to start backend:', err);
    });

    backendProcess.on('exit', (code) => {
        console.log(`Backend process exited with code ${code}`);
    });
}

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 1400,
        height: 900,
        icon: path.join(__dirname, '../public/DocuMate.ico'),
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            webSecurity: false,
            enableRemoteModule: true
        },
        show: false,
        backgroundColor: '#1e1e1e'
    });

    // Remove default menu
    Menu.setApplicationMenu(null);

    if (isDev) {
        console.log('Loading from dev server: http://localhost:5173');
        mainWindow.loadURL('http://localhost:5173');
        mainWindow.webContents.openDevTools();
    } else {
        // For file:// protocol, we need to fix React Router
        const indexPath = path.join(__dirname, '../dist/index.html');
        console.log('Loading production file:', indexPath);
        
        // Fix for React Router - load file then fix URL
        mainWindow.loadFile(indexPath).then(() => {
            console.log('File loaded, fixing URL for React Router...');
            
            // Execute JavaScript to fix the URL for React Router
            mainWindow.webContents.executeJavaScript(`
                // Fix for React Router in file:// protocol
                console.log('Before fix:', window.location.href);
                if (window.location.pathname.includes('/dist/index.html')) {
                    window.history.replaceState({}, '', '/');
                    console.log('After fix:', window.location.href);
                }
                
                // Also ensure base tag exists
                if (!document.querySelector('base')) {
                    const base = document.createElement('base');
                    base.href = './';
                    document.head.prepend(base);
                    console.log('Added base tag');
                }
            `).catch(err => {
                console.error('Failed to fix URL:', err);
            });
        }).catch(err => {
            console.error('Failed to load file:', err);
            
            // Fallback: Load with hash router
            const { format } = require('url');
            const fileUrl = format({
                pathname: indexPath,
                protocol: 'file:',
                slashes: true,
                hash: '/'
            });
            console.log('Trying fallback with hash:', fileUrl);
            mainWindow.loadURL(fileUrl);
        });
    }

    // Show when ready
    mainWindow.once('ready-to-show', () => {
        mainWindow.show();
        mainWindow.maximize();
    });

    // Handle load errors
    mainWindow.webContents.on('did-fail-load', (event, errorCode, errorDescription) => {
        console.error('Failed to load:', errorCode, errorDescription);
    });

    mainWindow.on('closed', () => {
        mainWindow = null;
    });
}

app.whenReady().then(() => {
    console.log('🚀 DocuMate starting...');
    
    // Start backend first
    startBackendServer();
    
    // Wait for backend, then create window
    setTimeout(() => {
        createWindow();
    }, isDev ? 1000 : 3000);
});

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        if (backendProcess) backendProcess.kill();
        app.quit();
    }
});

app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
        createWindow();
    }
});
app.on('before-quit', () => {
    if (backendProcess) backendProcess.kill();
});