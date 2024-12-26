import ThemeContextProvider from "@/contexts/ThemeContextProvider.tsx";
import {ThemeToggle} from "@/components/ThemeToggle.tsx";

function App() {

    return (
        <>
            <ThemeContextProvider>
                <ThemeToggle />
            </ThemeContextProvider>
        </>
    )
}

export default App
