import ThemeContextProvider from "@/contexts/ThemeContextProvider.tsx";
import {ThemeToggle} from "@/components/ThemeToggle.tsx";
import LogoutBtn from "@/components/LogoutBtn.tsx";

function App() {

  return (
    <>
      <ThemeContextProvider>
        <ThemeToggle/>
        <LogoutBtn/>
      </ThemeContextProvider>
    </>
  )
}

export default App
