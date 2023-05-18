package com.example.web.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafUtil {

    private static final TemplateEngine engine;
    static  {
        engine = new TemplateEngine();
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        r.setCharacterEncoding("utf-8");
        engine.setTemplateResolver(r);
    }

    public static TemplateEngine getEngine() {
        return engine;
    }
}
