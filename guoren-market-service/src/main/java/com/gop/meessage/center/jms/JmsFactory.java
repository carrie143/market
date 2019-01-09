
package com.gop.meessage.center.jms;

public interface JmsFactory<T>
{
    
    public T get();

 
    public void Destory();

}
