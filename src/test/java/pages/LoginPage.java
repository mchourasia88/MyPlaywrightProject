package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    // Locators
    private String usernameInput = "#username";
    private String passwordInput = "#password";
    private String submitButton = "#submit";
    private String logoutButton = "text=Log out";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername(String username) {
        page.fill(usernameInput, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordInput, password);
    }

    public void clickSubmit() {
        page.click(submitButton);
    }

    public boolean isLogoutVisible() {
        return page.isVisible(logoutButton);
    }
}
