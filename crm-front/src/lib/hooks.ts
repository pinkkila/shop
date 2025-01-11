import {useContext} from "react";
import {ThemeContext} from "@/contexts/theme-context.ts";

export function useTheme() {
  const context = useContext(ThemeContext);

  // FIXME After zustand refactor make sure that this works correctly
  if (context === undefined)
    throw new Error("useTheme must be used within a ThemeProvider");

  return context;
}