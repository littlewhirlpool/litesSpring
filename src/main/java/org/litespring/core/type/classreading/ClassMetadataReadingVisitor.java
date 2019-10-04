package org.litespring.core.type.classreading;

import org.litespring.core.type.ClassMetadata;
import org.litespring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;


// ClassVisitor为ASM的类 其子类能作为参数调用reader的accept方法 然后
// ClassMetadata为自定义的接口 定义对类元数据的一些操作
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata  /*implements ClassMetadata*/ {

    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;



    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }


    /**
     * reader 调用此方法将类的信息传递过来 , 处理后封装到自己的属性中去
     * ClassVisitor
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param supername
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }


    public String getClassName() {
        return this.className;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isConcrete() {
        return !(this.isInterface || this.isAbstract);
    }

    public boolean isFinal() {
        return this.isFinal;
    }


    public boolean hasSuperClass() {
        return (this.superClassName != null);
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaceNames() {
        return this.interfaces;
    }


}
