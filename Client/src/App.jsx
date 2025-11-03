import './App.css'
import Home from './pages/Home'
import { Routes, Route } from 'react-router-dom';
import ReadMeComparison from './pages/ReadMeComparison'
import GitIgnoreCompare from './pages/GitIgnoreCompare'
import EnvCompare from './pages/EnvCompare'
import DockerCompare from './pages/DockerCompare';
import ViewReadme from './pages/ViewReadme';
import CreateorEditenv from './pages/CreateorEditenv';
import CreateorEditdocker from './pages/CreateorEditdocker';
import CreateorEditgit from './pages/CreateorEditgit';






function App() {


  return (
    <>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/home' element={<Home />} />
        <Route path='/ReadMe' element={<ReadMeComparison />} />
        <Route path='/gitignore' element={<GitIgnoreCompare />} />
        <Route path='/env' element={<EnvCompare />} />
        <Route path='/editenv' element={<CreateorEditenv />} />
        <Route path='/editdocker' element={<CreateorEditdocker />} />
        <Route path='/editgit' element={<CreateorEditgit />} />
        <Route path='/docker' element={<DockerCompare />} />
        <Route path='/latestReadme/:id' element={<ViewReadme />} />
      </Routes>
    </>
  )
}

export default App
