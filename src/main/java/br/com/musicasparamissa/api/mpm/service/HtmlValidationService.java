package br.com.musicasparamissa.api.mpm.service;

import nu.validator.messages.MessageEmitter;
import nu.validator.messages.MessageEmitterAdapter;
import nu.validator.messages.TextMessageEmitter;
import nu.validator.servlet.imagereview.ImageCollector;
import nu.validator.source.SourceCode;
import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.xml.SystemErrErrorHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class HtmlValidationService {

    public boolean validateHtml(String html) {
        try {
            InputStream in = new ByteArrayInputStream( html.getBytes( "UTF-8" ));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            SourceCode sourceCode = new SourceCode();
            ImageCollector imageCollector = new ImageCollector(sourceCode);
            boolean showSource = false;
            MessageEmitter emitter = new TextMessageEmitter( out, false );
            MessageEmitterAdapter errorHandler = new MessageEmitterAdapter( sourceCode, showSource, imageCollector, 0, false, emitter );
            errorHandler.setErrorsOnly( true );

            SimpleDocumentValidator validator = new SimpleDocumentValidator();

            validator.setUpMainSchema( "http://s.validator.nu/html5-rdfalite.rnc", new SystemErrErrorHandler());
            validator.setUpValidatorAndParsers( errorHandler, true, false );
            validator.checkXmlInputSource( new InputSource( in ));

            return errorHandler.getFatalErrors() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
