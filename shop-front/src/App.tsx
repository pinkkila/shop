import {ThemeToggle} from "@/components/ThemeToggle.tsx";
import ThemeContextProvider from "@/contexts/ThemeContextProvider.tsx";

function App() {

    return (
        <>
            <ThemeContextProvider>
                <ThemeToggle/>
            </ThemeContextProvider>
        </>
    )
}

export default App
