// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package org.drools.event.process;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class ProcessEventManager_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return org.drools.event.process.ProcessEventManager_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        org.drools.event.process.ProcessEventManager_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __ProcessEventManager extends system.Object implements org.drools.event.process.ProcessEventManager {
    
    protected __ProcessEventManager(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(Lorg/drools/event/process/ProcessEventListener;)V")
    public native void addEventListener(org.drools.event.process.ProcessEventListener par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lorg/drools/event/process/ProcessEventListener;)V")
    public native void removeEventListener(org.drools.event.process.ProcessEventListener par0);
    
    @net.sf.jni4net.attributes.ClrMethod("()Ljava/util/Collection;")
    public native java.util.Collection getProcessEventListeners();
}
//</generated-proxy>
