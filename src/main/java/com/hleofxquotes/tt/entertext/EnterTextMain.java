package com.hleofxquotes.tt.entertext;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class EnterTextMain {

	public static void main(String[] args) {
		EnterTextMain enterTextMain = new EnterTextMain();

		String title = "turbotax";
		String text = "abcdefg12345678";

		enterTextMain.enterText(title, text);
	}

	public void enterText(String title, String text) {
		User32.INSTANCE.EnumWindows((hWnd, data) -> {
			String wTitle = getWindowTitle(hWnd);

			// Find window with title starting with downcase "turbotax" string
			if (wTitle.startsWith(title)) {
				System.out.println("Found window with title=" + title);

				enterText(text, hWnd);
				return false; // Found done;
			}

			return true; // Keep searching

		}, null);
	}

	private String getWindowTitle(HWND hWnd) {
		char[] name = new char[512];

		User32.INSTANCE.GetWindowText(hWnd, name, name.length);

		String title = Native.toString(name).toLowerCase();
		return title;
	}

	private void enterText(String text, HWND hWnd) {
		// Bring the window to the front
		User32.INSTANCE.SetForegroundWindow(hWnd);

		for (char c : text.toCharArray()) {
			// process c
			int charUnicodeDecimalValue = c; // Get the unicode decimal

			pressKeyboard(charUnicodeDecimalValue);
		}
	}

	private void pressKeyboard(int unicodechar) {
		System.out.println("pressKeyboard, unicodechar=" + unicodechar);

		sendKeyDown(unicodechar);

		sendKeyUp(unicodechar);
	}

	private void sendKeyUp(int unicodechar) {
		WinUser.INPUT input;
		input = new WinUser.INPUT();

		input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		// Because setting INPUT_INPUT_KEYBOARD is not enough:
		// https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ

		input.input.setType("ki");

		input.input.ki.time = new WinDef.DWORD(0);
		input.input.ki.dwFlags = new WinDef.DWORD(
				WinUser.KEYBDINPUT.KEYEVENTF_UNICODE | WinUser.KEYBDINPUT.KEYEVENTF_KEYUP); // I am handing you a
																							// unicode
																							// character
		input.input.ki.wScan = new WinDef.WORD(unicodechar);
		input.input.ki.wVk = new WinDef.WORD(0); // Virtual key code, i am setting this to 0 because of the unicode flag
													// in dwFlags

		input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);

		User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
	}

	private void sendKeyDown(int unicodechar) {
		WinUser.INPUT input;
		input = new WinUser.INPUT();

		input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		// Because setting INPUT_INPUT_KEYBOARD is not enough:
		// https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
		input.input.setType("ki");

		input.input.ki.time = new WinDef.DWORD(0);
		input.input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_UNICODE); // I am handing you a unicode
																							// character
		input.input.ki.wScan = new WinDef.WORD(unicodechar);
		input.input.ki.wVk = new WinDef.WORD(0); // Virtual key code, i am setting this to 0 because of the unicode flag
													// in dwFlags

		input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);

		User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
	}

}
