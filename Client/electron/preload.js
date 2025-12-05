import { contextBridge, ipcRenderer } from 'electron'

contextBridge.exposeInMainWorld('electronAPI', {
  // Platform info
  platform: process.platform,
  isPackaged: process.argv.some(arg => arg.includes('app.asar')),
  
  // Communication with main process
  send: (channel, data) => ipcRenderer.send(channel, data),
  receive: (channel, func) => ipcRenderer.on(channel, (event, ...args) => func(...args)),
  
  // App info
  getAppVersion: () => ipcRenderer.invoke('get-app-version'),
  relaunchApp: () => ipcRenderer.send('relaunch-app')
})