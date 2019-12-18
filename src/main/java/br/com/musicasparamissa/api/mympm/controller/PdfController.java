package br.com.musicasparamissa.api.mympm.controller;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController("MyMpMPdfController")
@RequestMapping("/mympm/pdf")
public class PdfController {

    @PostMapping
    public void generate(@RequestBody String html, HttpServletResponse response) throws Exception {

        System.out.println(html);
        System.out.println("Generate PDF");

        response.setHeader("Content-type", "application/pdf");

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, "https://musicasparamissa.com.br/");
        builder.toStream(response.getOutputStream());
        builder.run();

    }

}