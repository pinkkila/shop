import {ReactNode, useEffect, useState} from "react"
import { ThemeContext } from "./theme-context"

type Theme = "dark" | "light" | "system"

export default function ThemeContextProvider({children}: { children: ReactNode }) {
    const [theme, setTheme] = useState<Theme>(
        () => localStorage.getItem("ui-theme") as Theme || "system"
    );

    useEffect(() => {
        const root = window.document.documentElement

        root.classList.remove("light", "dark")

        if (theme === "system") {
            const systemTheme = window.matchMedia("(prefers-color-scheme: dark)")
                .matches
                ? "dark"
                : "light"

            root.classList.add(systemTheme)
            return
        }

        root.classList.add(theme)
    }, [theme])

    const handleSetTheme = (newTheme: Theme) => {
        localStorage.setItem("ui-theme", newTheme);
        setTheme(newTheme);
    };

    return (
        <ThemeContext.Provider value={{
            theme,
            setTheme: handleSetTheme,
        }}>
            {children}
        </ThemeContext.Provider>
    )
}