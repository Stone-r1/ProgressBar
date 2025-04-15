# Terminal file line counter with a simple progress bar

A Java command-line tool that searches a given directory, filters files by extension, and counts the total number of lines of code. It features a live terminal progress bar that updates as each file is processed.

---

## How to Run

1. **Compile:**

   ```bash
   javac main.java
   ```

2. **Run:**

   ```bash
   java Main
   ```

3. **Follow the prompts:**

   - Enter the full path to the directory (absolute or relative).
   - Enter the file extension you want to filter (e.g., `cpp`, `py`, `java`).

---

## Example

```
Enter the full path to the directory
/home/user/projects
Enter file extension to search (cpp, py, etc.)
java
Found 12 files
[##############--------------] 50%
There are exactly 727 lines of code in total!
```
---
