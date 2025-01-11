import {createContext} from "react";

type Theme = "dark" | "light" | "system"

type TThemeProvider = {
  theme: Theme
  setTheme: (theme: Theme) => void
}

const initialState: TThemeProvider = {
  theme: "system",
  setTheme: () => null,
}

export const ThemeContext = createContext<TThemeProvider>(initialState)