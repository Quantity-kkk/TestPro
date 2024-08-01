package com.kyq.test.annotation.source;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;


/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-10 19:15
 * @since 1.8
 */

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.kyq.test.annotation.source.SourceAnnotationEx")
public class SelfDefSourceAnnotationProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    private volatile int r = 1;//轮循次数

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //初始化Filer和Messager
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, " process() is execute...");
        //Get all the compile classes Elements,and print they
        Set<? extends Element> elements = roundEnv.getRootElements();
        System.out.println("All input Classes: ");
        for(Element e: elements){
            System.out.println(">>> "+e.getSimpleName());
        }

        //get used @SourceAnnotationEx class..
        System.out.println("Will generate interfaces's classes: ");
        Set<? extends Element> genElements = roundEnv.getElementsAnnotatedWith(SourceAnnotationEx.class);
        for(Element e: genElements){
            System.out.println(">>> "+e.getSimpleName());
            SourceAnnotationEx gi = e.getAnnotation(SourceAnnotationEx.class);
            String className = e.getSimpleName()+gi.source();
            String classString =
                    "package com.kyq.test.annotation.source;\n"
                            +"public interface "+className+" {\n";
            //Get all the methods elements
            List<? extends Element> genElementAlls = e.getEnclosedElements();
            System.out.println(">>>> Class "+e.getSimpleName()
                    +"Packaging(Only decorated by 'public' methods can generate interface methods:");
            for(Element e1 : genElementAlls){
                System.out.println(">>> >>>"+e1.getSimpleName()+" Modifier: "+e1.getModifiers());
                if(!e1.getSimpleName().toString().equals("<init>")
                        && e1.asType() instanceof ExecutableType && isPublic(e1)){
                    System.out.println(">>> >>> >>> "+e1.getSimpleName());
                    classString += "    void "+e1.getSimpleName()+"();\n";
                }
            }
            classString+="}";
            try {
                JavaFileObject jfo = filer
                        .createSourceFile("main.java.com.kyq.test.annotation.source."+className, e);
                Writer writer = jfo.openWriter();
                writer.flush();
                writer.append(classString);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("-------------------annotation processor round "+(r++)+" cycle ended...\n");
        return true;
    }


    //判断元素是否为public
    public boolean isPublic(Element e){
        //获取元素的修饰符Modifier,注意此处的Modifier
        //非java.lang.reflect.Modifier
        Set<Modifier> modifiers = e.getModifiers();
        for(Modifier m: modifiers){
            if(m.equals(Modifier.PUBLIC)) return true;
        }
        return false;
    }
}
