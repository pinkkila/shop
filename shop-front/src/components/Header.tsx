import {ThemeToggle} from "@/components/ThemeToggle.tsx";

function Header() {
  return (
    <div className="flex items-center justify-between ">
      <h1>Shop</h1>
      <ThemeToggle/>
    </div>
  );
}

export default Header;