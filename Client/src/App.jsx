import "./App.css";
import Home from "./pages/Home";
import { Routes, Route } from "react-router-dom";
import ReadMeComparison from "./pages/ReadMeComparison";
import GitIgnoreCompare from "./pages/GitIgnoreCompare";
import EnvCompare from "./pages/EnvCompare";
import DockerCompare from "./pages/DockerCompare";
import ViewReadme from "./pages/ViewReadme";
import CreateorEditenv from "./pages/CreateorEditenv";
import CreateorEditdocker from "./pages/CreateorEditdocker";
import CreateorEditgit from "./pages/CreateorEditgit";
import About from "./pages/About";
import Faqs from "./pages/Faqs";
import DocumateWorkflow from "./pages/DocumateWorkflow";
import Register from "./pages/Register";
import PrerequisitePage from "./pages/PrerequisitePage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <>
      <ToastContainer
        position="bottom-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
      />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/ReadMe" element={<ReadMeComparison />} />
        <Route path="/gitignore" element={<GitIgnoreCompare />} />
        <Route path="/env" element={<EnvCompare />} />
        <Route path="/editenv" element={<CreateorEditenv />} />
        <Route path="/editdocker" element={<CreateorEditdocker />} />
        <Route path="/editgit" element={<CreateorEditgit />} />
        <Route path="/editreadme" element={<ReadMeComparison />} />
        <Route path="/docker" element={<DockerCompare />} />
        <Route path="/latestReadme/:id" element={<ViewReadme />} />
        <Route path="/about" element={<About />} />
        <Route path="/faqs" element={<Faqs />} />
        <Route path="/workflow" element={<DocumateWorkflow />} />
        <Route path="/prerequisite" element={<PrerequisitePage />} />
      </Routes>
    </>
  );
}

export default App;
