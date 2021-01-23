package zhuj.http.response;

import com.zhuj.code.file.FileIO;
import com.zhuj.code.file.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;

public class FileResponseParser implements ResponseParser<File> {
    private String filePath;

    public FileResponseParser(String filePath) {
        this.filePath = filePath;
    }

    public File parse(Response response) {
        try {
            InputStream ret = response.body() != null ? response.body().byteStream() : null;
            if (ret != null) {
                FileOutputStream fout = new FileOutputStream(filePath);
                IOUtils.write(ret, fout);
                return new File(filePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
