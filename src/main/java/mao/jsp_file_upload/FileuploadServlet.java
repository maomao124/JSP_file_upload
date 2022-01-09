package mao.jsp_file_upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Project name(项目名称)：JSP_file_upload
 * Package(包名): mao.jsp_file_upload
 * Class(类名): FileuploadServlet
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/1/9
 * Time(创建时间)： 20:35
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@WebServlet("/FileuploadServlet")
public class FileuploadServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
    // 设置缓冲区大小
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    // 设置上传单个文件的最大值
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * 文件上传判断逻辑
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // 判断是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request))
        {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
        // 配置fileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置上传文件大小的最大值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置上传文件总量的最大值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 防止上传文件名称乱码
        upload.setHeaderEncoding("UTF-8");
        // 构造临时路径来存储上传的文件
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
        {
            uploadDir.mkdir();
        }
        try
        {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0)
            {
                // 迭代表单数据
                for (FileItem item : formItems)
                {
                    // 处理表单中不存在的字段
                    if (!item.isFormField())
                    {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message", "文件上传成功!");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            request.setAttribute("message", "错误信息: " + ex.getMessage());
        }
        // 跳转到 message.jsp
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
