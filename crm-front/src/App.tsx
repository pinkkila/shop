import {Button} from "@/components/ui/button.tsx";
import {ThemeToggle} from "@/components/ThemeToggle.tsx";
import ThemeContextProvider from "@/contexts/ThemeContextProvider.tsx";

function App() {

    return (
        <>
            <ThemeContextProvider>
                <Button>Click me</Button>
                <ThemeToggle/>
            </ThemeContextProvider>
        </>
    )
}

export default App
