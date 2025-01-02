import {Button} from "@/components/ui/button.tsx";

function LogoutBtn() {

  const csrfToken = document.cookie.replace(
    /(?:(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$)|^.*$/,
    "$1"
  );

  const handleLogout = async () => {
    try {
      const response = await fetch("http://127.0.0.1:8080/logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "X-XSRF-TOKEN": csrfToken,
        },
        credentials: "include",
      });

      if (response.ok) {
        const data = await response.json();
        window.location.href = data.logoutUrl;
      } else {
        alert("Uloskirjautuminen epäonnistui.");
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Button onClick={handleLogout}>Logout</Button>
  );
}

export default LogoutBtn;