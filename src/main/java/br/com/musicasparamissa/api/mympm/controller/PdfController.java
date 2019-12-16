package br.com.musicasparamissa.api.mympm.controller;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController("MyMpMPdfController")
@RequestMapping("/mympm/pdf")
public class PdfController {

    @GetMapping
    public void generate(@RequestBody String html, HttpServletResponse response) throws Exception {

        System.out.println(html);
        System.out.println("Generate PDF");

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, "https://musicasparamissa.com.br/");
        builder.toStream(response.getOutputStream());
        builder.run();

    }

}