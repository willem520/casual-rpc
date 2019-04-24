package willem.weiyu.casual.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import willem.weiyu.casual.Transporter;
import willem.weiyu.casual.common.CasualRequest;

/**
 * @Author weiyu
 * @Description
 * @Date 2019/4/24 11:34
 */
public class CasualInvoker<T> implements InvocationHandler {
    private Class<T> clazz;

    public CasualInvoker(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CasualRequest request = new CasualRequest();
        String requestId = UUID.randomUUID().toString();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterType(parameterTypes);
        request.setParameters(args);

        return Transporter.send(request).getResult();
    }
}