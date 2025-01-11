import {ThemeToggle} from "@/components/ThemeToggle.tsx";
import LogoutBtn from "@/components/LogoutBtn.tsx";

function Header() {
  return (
    <div className="flex items-center justify-between ">
      <h1>Customer Relationship Management Console</h1>
      <LogoutBtn />
      <ThemeToggle/>
    </div>
  );
}

export default Header;