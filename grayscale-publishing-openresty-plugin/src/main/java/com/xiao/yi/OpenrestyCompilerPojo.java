package com.xiao.yi;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * @author xiaoyi
 * @since 2023/2/21
 */
@Mojo(name = "openresty-compiler")
public class OpenrestyCompilerPojo extends AbstractMojo {

    @Parameter(name = "deletePaths", required = true)
    private String[] deletePaths;

    @Parameter(name = "copyPaths", required = true)
    private List<CopyFileProperties> copyPaths;

    public OpenrestyCompilerPojo() {
    }

    public void execute() throws MojoExecutionException {
        for (String deletePath : deletePaths) {
            File deleteFile = new File(deletePath);
            if (deleteFile.exists()) {
                deleteFile(deleteFile);
            }
        }

        for (CopyFileProperties copyPath : copyPaths) {
            File sourceFile = new File(copyPath.getSource());
            File targetFile = new File(copyPath.getTarget());
            getLog().info("source: " + copyPath.getSource() + " , target: " + copyPath.getTarget());
            if (!sourceFile.exists()) {
                throw new RuntimeException(copyPath.getSource() + " is not exists");
            }

            if (sourceFile.isDirectory()) {
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                copyDirectory(sourceFile, targetFile);
            } else {
                File targetParentFile = targetFile.getParentFile();
                if (!targetParentFile.exists()) {
                    targetParentFile.mkdirs();
                }
                copyFile(sourceFile, targetFile);
            }
        }
    }

    private void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (null != files) {
                for (File child : files) {
                    deleteFile(child);
                }
            }
            file.delete();
        }
    }

    private void copyDirectory(File sourceDir, File targetFile) {
        File[] children = sourceDir.listFiles();
        for (File child : children) {
            File targetChildFile = new File(targetFile + File.separator + child.getName());
            if (child.isDirectory()) {
                targetChildFile.mkdir();
                copyDirectory(child, targetChildFile);
            } else {
                try {
                    targetChildFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("create " + targetChildFile.getParentFile() + " fail", e);
                }
                copyFile(child, targetChildFile);
            }
        }
    }

    private void copyFile(File source, File target) {
        try (InputStream inputStream = Files.newInputStream(source.toPath());
             OutputStream outputStream = Files.newOutputStream(target.toPath())) {
            byte[] bytes = new byte[8 * 1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("copy file fail", e);
        }
    }

}
