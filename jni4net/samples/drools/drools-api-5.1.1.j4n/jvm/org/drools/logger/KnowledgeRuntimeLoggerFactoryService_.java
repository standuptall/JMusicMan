// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package org.drools.logger;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class KnowledgeRuntimeLoggerFactoryService_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return org.drools.logger.KnowledgeRuntimeLoggerFactoryService_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        org.drools.logger.KnowledgeRuntimeLoggerFactoryService_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __KnowledgeRuntimeLoggerFactoryService extends system.Object implements org.drools.logger.KnowledgeRuntimeLoggerFactoryService {
    
    protected __KnowledgeRuntimeLoggerFactoryService(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(Lorg/drools/event/KnowledgeRuntimeEventManager;Ljava/lang/String;)Lorg/drools/logger/KnowledgeRuntimeLogger;")
    public native org.drools.logger.KnowledgeRuntimeLogger newFileLogger(org.drools.event.KnowledgeRuntimeEventManager par0, java.lang.String par1);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lorg/drools/event/KnowledgeRuntimeEventManager;Ljava/lang/String;I)Lorg/drools/logger/KnowledgeRuntimeLogger;")
    public native org.drools.logger.KnowledgeRuntimeLogger newThreadedFileLogger(org.drools.event.KnowledgeRuntimeEventManager par0, java.lang.String par1, int par2);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lorg/drools/event/KnowledgeRuntimeEventManager;)Lorg/drools/logger/KnowledgeRuntimeLogger;")
    public native org.drools.logger.KnowledgeRuntimeLogger newConsoleLogger(org.drools.event.KnowledgeRuntimeEventManager par0);
}
//</generated-proxy>
