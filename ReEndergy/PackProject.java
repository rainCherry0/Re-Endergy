import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class PackProject {

    // ================= 配置区域 (Configuration) =================

    // 1. 输出文件名
    private static final String OUTPUT_FILE = "project_context.txt";

    // 2. 文件夹黑名单 (不管在哪一层，只要叫这个名字就被忽略)
    private static final Set<String> IGNORED_DIRS = new HashSet<>(Arrays.asList(
            ".git", ".idea", ".gradle", "build", "out", "bin", "run", "gradle", ".settings", "logs", ".vscode", "wrapper"
    ));

    // 3. 【新增】指定屏蔽的相对路径文件夹
    // 这些路径下的内容（包括路径本身）都会被忽略。
    // 请使用 "/" 作为分隔符，即使在 Windows 上也是如此。
    private static final Set<String> IGNORED_SPECIFIC_PATHS = new HashSet<>(Arrays.asList(
            "src/generated",             // 举例：忽略自动生成的代码目录
            "src/main/resources/assets", // 举例：忽略资源材质目录
            "run/saves"                  // 举例：忽略存档
    ));

    // 4. 文件名黑名单 (严格匹配 "文件名+后缀")
    private static final Set<String> BLACKLIST_FILENAMES = new HashSet<>(Arrays.asList(
            "gradlew",              // Gradle Linux/Mac 脚本
            "gradlew.bat",          // Gradle Windows 脚本
            "PackProject.java",     // 脚本自身
            "TEMPLATE_LICENSE.txt",
            "neoforge.mods.toml",
            OUTPUT_FILE
    ));

    // 5. 忽略的文件后缀 (完全忽略，不出现在树中)
    private static final Set<String> IGNORED_EXTENSIONS = new HashSet<>(Arrays.asList(
            ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".ico", ".tif", ".tiff", ".tga", ".xcf"
    ));

    // 6. 二进制扩展名 (只显示在树中，不读取内容)
    private static final Set<String> BINARY_EXTENSIONS = new HashSet<>(Arrays.asList(
            ".jar", ".class",
            ".zip", ".gz", ".tar", ".7z", ".rar", ".exe", ".dll", ".so",
            ".ogg", ".mp3", ".wav"
    ));

    // ================= 主程序 (Main Logic) =================

    public static void main(String[] args) {
        File rootDir = new File(".");
        File outputFile = new File(rootDir, OUTPUT_FILE);

        System.out.println("Start packing project from: " + rootDir.getAbsolutePath());

        StringBuilder sb = new StringBuilder();
        sb.append("Project Context Breakdown:\n");
        sb.append("Root: ").append(rootDir.getAbsoluteFile().getParentFile().getName()).append("\n");
        sb.append("------------------------------------------------------------\n\n");

        // 1. 生成树状结构
        sb.append("Directory Structure:\n.\n");
        generateTree(rootDir, rootDir, "", sb); // 注意：这里多传了一个 rootDir
        sb.append("\n------------------------------------------------------------\n\n");

        // 2. 读取文件内容
        dumpFiles(rootDir, rootDir, sb);

        // 3. 写入文件
        try {
            Files.writeString(outputFile.toPath(), sb.toString(), StandardCharsets.UTF_8);
            System.out.println("Done! Output saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= 辅助方法 (Helpers) =================

    /**
     * 递归生成目录树
     */
    private static void generateTree(File dir, File rootDir, String indent, StringBuilder sb) {
        File[] files = dir.listFiles();
        if (files == null) return;

        Arrays.sort(files, Comparator.comparing(File::getName));

        for (File f : files) {
            // 核心过滤逻辑，传入 rootDir 计算相对路径
            if (shouldIgnore(f, rootDir)) continue;

            sb.append(indent);
            sb.append("├── ");
            sb.append(f.getName());
            if (f.isDirectory()) {
                sb.append("/");
            }
            sb.append("\n");

            if (f.isDirectory()) {
                generateTree(f, rootDir, indent + "│   ", sb);
            }
        }
    }

    /**
     * 递归读取文件内容
     */
    private static void dumpFiles(File dir, File rootDir, StringBuilder sb) {
        File[] files = dir.listFiles();
        if (files == null) return;

        Arrays.sort(files, Comparator.comparing(File::getName));

        for (File f : files) {
            // 核心过滤逻辑
            if (shouldIgnore(f, rootDir)) continue;

            if (f.isDirectory()) {
                dumpFiles(f, rootDir, sb);
            } else {
                if (isTextFile(f)) {
                    readFileContent(f, rootDir, sb);
                }
            }
        }
    }

    /**
     * 判断是否应该忽略该文件/文件夹
     */
    private static boolean shouldIgnore(File f, File rootDir) {
        String name = f.getName();

        // 1. 忽略 . 开头的隐藏文件
        if (name.startsWith(".")) return true;

        // 2. 文件夹黑名单 (简单的名字匹配)
        if (f.isDirectory() && IGNORED_DIRS.contains(name)) return true;

        // 3. 文件名黑名单
        if (f.isFile() && BLACKLIST_FILENAMES.contains(name)) return true;

        // 4. 后缀名黑名单
        if (f.isFile()) {
            String nameLower = name.toLowerCase();
            for (String ext : IGNORED_EXTENSIONS) {
                if (nameLower.endsWith(ext)) return true;
            }
        }

        // 5. 【新增】指定相对路径黑名单
        // 计算相对路径，并统一分隔符为 "/"
        String relPath = rootDir.toPath().relativize(f.toPath()).toString().replace("\\", "/");

        // 检查是否在屏蔽列表中，或者是屏蔽列表的子路径
        for (String ignoredPath : IGNORED_SPECIFIC_PATHS) {
            // 精确匹配目录或文件
            if (relPath.equals(ignoredPath)) return true;
            // 匹配子目录 (例如 ignoredPath="src/gen", relPath="src/gen/test.java")
            if (relPath.startsWith(ignoredPath + "/")) return true;
        }

        return false;
    }

    /**
     * 判断是否为文本文件
     */
    private static boolean isTextFile(File f) {
        String name = f.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot == -1) return true;

        String ext = name.substring(lastDot).toLowerCase();
        return !BINARY_EXTENSIONS.contains(ext);
    }

    /**
     * 读取单个文件
     */
    private static void readFileContent(File f, File rootDir, StringBuilder sb) {
        try {
            String relPath = rootDir.toPath().relativize(f.toPath()).toString().replace("\\", "/");
            String content = Files.readString(f.toPath(), StandardCharsets.UTF_8);

            sb.append(">>>>>>>>> FILE_START: ").append(relPath).append(" <<<<<<<<<\n");
            sb.append(content);
            if (!content.endsWith("\n")) {
                sb.append("\n");
            }
            sb.append(">>>>>>>>> FILE_END: ").append(relPath).append(" <<<<<<<<<\n\n");

        } catch (IOException e) {
            System.err.println("Error reading file " + f.getName() + ": " + e.getMessage());
        }
    }
}
