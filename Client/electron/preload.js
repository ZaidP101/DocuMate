const { contextBridge, ipcRenderer } = require('electron'); 

contextBridge.exposeInMainWorld('api', {
    // future APIs can go here
});
