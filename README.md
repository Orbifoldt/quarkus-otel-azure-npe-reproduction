# quarkus-otel-azure-npe

Build and package the application
```
./mvnw clean package
```

Run the jar with the application insights agent
```bash
java -javaagent:applicationinsights-agent-3.4.14.jar -jar ./target/quarkus-app/quarkus-run.jar
```
NB: while the connection string in the [applicationinsights.json](applicationinsights.json) is invalid, the behaviour is the same. 

Now make a `GET` to the `/hello` endpoint, this invokes a simple rest client that fetches the quarkus extensions (see [ExtensionsService.kt](src/main/kotlin/com/example/ExtensionsService.kt))
```bash
curl http://localhost:8080/hello
```

This then causes the following exception:
```
 java.lang.NullPointerException: Cannot invoke "io.opentelemetry.context.Context.get(io.opentelemetry.context.ContextKey)" because "context" is null
        at org.jboss.resteasy.core.ExceptionHandler.handleApplicationException(ExceptionHandler.java:107)
        at org.jboss.resteasy.core.ExceptionHandler.handleException(ExceptionHandler.java:344)
        at org.jboss.resteasy.core.SynchronousDispatcher.writeException(SynchronousDispatcher.java:205)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:452)
        at org.jboss.resteasy.core.SynchronousDispatcher.lambda$invoke$4(SynchronousDispatcher.java:240)
        at org.jboss.resteasy.core.SynchronousDispatcher.lambda$preprocess$0(SynchronousDispatcher.java:154)
        at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:321)
        at org.jboss.resteasy.core.SynchronousDispatcher.preprocess(SynchronousDispatcher.java:157)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:229)
        at io.quarkus.resteasy.runtime.standalone.RequestDispatcher.service(RequestDispatcher.java:82)
        at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler.dispatch(VertxRequestHandler.java:147)
        at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler$1.run(VertxRequestHandler.java:93)
        at io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:576)
        at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)
        at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)
        at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)
        at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: java.lang.NullPointerException: Cannot invoke "io.opentelemetry.context.Context.get(io.opentelemetry.context.ContextKey)" because "context" is null
        at io.opentelemetry.instrumentation.api.instrumenter.http.HttpClientResend.getAndIncrement(HttpClientResend.java:36)
        at io.opentelemetry.instrumentation.api.instrumenter.http.HttpClientAttributesExtractor.onEnd(HttpClientAttributesExtractor.java:161)
        at io.opentelemetry.instrumentation.api.instrumenter.Instrumenter.doEnd(Instrumenter.java:224)
        at io.opentelemetry.instrumentation.api.instrumenter.Instrumenter.end(Instrumenter.java:144)
        at io.quarkus.opentelemetry.runtime.tracing.intrumentation.restclient.OpenTelemetryClientFilter.filter(OpenTelemetryClientFilter.java:123)
        at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.filterResponse(ClientInvocation.java:667)
        at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.invoke(ClientInvocation.java:428)
        at org.jboss.resteasy.client.jaxrs.internal.proxy.ClientInvoker.invokeSync(ClientInvoker.java:134)
        at org.jboss.resteasy.client.jaxrs.internal.proxy.ClientInvoker.invoke(ClientInvoker.java:103)
        at org.jboss.resteasy.client.jaxrs.internal.proxy.ClientProxy.invoke(ClientProxy.java:61)
        at jdk.proxy3/jdk.proxy3.$Proxy72.getById(Unknown Source)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jboss.resteasy.microprofile.client.ProxyInvocationHandler.invoke(ProxyInvocationHandler.java:168)
        at jdk.proxy3/jdk.proxy3.$Proxy75.getById(Unknown Source)
        at com.example.ExampleResource.hello(ExampleResource.kt:16)
        at com.example.ExampleResource_Subclass.hello$$superforward(Unknown Source)
        at com.example.ExampleResource_Subclass$$function$$1.apply(Unknown Source)
        at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)
        at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)
        at io.quarkus.resteasy.runtime.QuarkusRestPathTemplateInterceptor.restMethodInvoke(QuarkusRestPathTemplateInterceptor.java:39)
        at io.quarkus.resteasy.runtime.QuarkusRestPathTemplateInterceptor_Bean.intercept(Unknown Source)
        at io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)
        at io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)
        at io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)
        at com.example.ExampleResource_Subclass.hello(Unknown Source)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:154)
        at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:118)
        at org.jboss.resteasy.core.ResourceMethodInvoker.internalInvokeOnTarget(ResourceMethodInvoker.java:560)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTargetAfterFilter(ResourceMethodInvoker.java:452)
        at org.jboss.resteasy.core.ResourceMethodInvoker.lambda$invokeOnTarget$2(ResourceMethodInvoker.java:413)
        at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:321)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTarget(ResourceMethodInvoker.java:415)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:378)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:356)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:70)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:429)
        ... 15 more
```
