import { contextBridge } from 'electron';

contextBridge.exposeInMainWorld('api', {
    // future APIs can go here
});
