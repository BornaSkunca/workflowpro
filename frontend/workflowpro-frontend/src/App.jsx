import './App.css'
import { BrowserRouter, Routes ,Route} from 'react-router-dom'
import LoginPage from "./pages/LoginPage"
import Dashboard from "./pages/Dashboard"
import ProtectedRoute from "./components/ProtectedRoute"
import ProjectsPage from "./pages/ProjectsPage"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route path="/projects" element={<ProtectedRoute><ProjectsPage/></ProtectedRoute>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
