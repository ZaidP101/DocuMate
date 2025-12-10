# DocuMate Installation Guide

## Quick Install (Windows)
1. Download `DocuMate Setup 1.0.0.exe` from Releases
2. Run the installer
3. Launch from Start Menu

## Development Setup
1. Clone repository
2. Backend: `cd backend && mvn clean package`
3. Frontend: `cd Client && npm install`
4. Run: `npm run dev:electron`

## Git Hook Setup
After installing DocuMate:
1. Open the app
2. Register your project
3. Git hooks will be auto-installed
4. Commit code → DocuMate opens automatically