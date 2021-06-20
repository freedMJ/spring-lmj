package com.spring;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringApplicationContext {

    private Class configClass;

    private  ConcurrentHashMap<String,Object> singletonObjects  = new ConcurrentHashMap();
    private  ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();

    public MySpringApplicationContext(Class configClass) {
        this.configClass = configClass;

        scope(configClass);


        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            BeanDefinition beanDefinition = beanDefinitionEntry.getValue();
            //假如是单例的
            if("singleton".equals(beanDefinition.getScope())){
                Object bean = createBean(beanName,beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }





    }

    private void scope(Class configClass) {
        //处理config
        //1、判断该类上是否有扫描注解
        ComponentScan componentScan = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);

        //获取扫描路径
        String componentScanUrl = componentScan.value();
        //获取自定义类加载器
        ClassLoader classLoader = MySpringApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(componentScanUrl.replace(".","/"));
        File file = new File(resource.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getAbsolutePath();
                //取出所有class文件
                if(fileName.endsWith(".class")){
                    //
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    className= className.replace("\\", ".");
                    try {
                        Class<?> aClass = classLoader.loadClass(className);
                        //判断这个类是否有component
                        if(aClass.isAnnotationPresent(Component.class)){
                            Component component = aClass.getDeclaredAnnotation(Component.class);
                            String beanName = component.value();

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(aClass);
                            //判断是否有作用域从单例池中获取
                            if(aClass.isAnnotationPresent(Scope.class)){
                                Scope scope = aClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scope.value());
                            }else{
                                //默认是单例
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName,beanDefinition);
                        }



                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object createBean(String beanName,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            //得到字段
            for (Field field : clazz.getDeclaredFields()) {
                //判断字段上是否有Autowired注解
                if(field.isAnnotationPresent(Autowried.class)){
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance,bean);
                }
                //判断是否需要设置beanName
                if(instance instanceof BeanNameAware){
                    ((BeanNameAware) instance).setBeanName(beanName);
                }
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName){

        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if("singleton".equals(beanDefinition.getScope())){
                Object o = singletonObjects.get(beanName);
                if (Objects.isNull(o)) {
                    return createBean(beanName,beanDefinition);
                }
                return o;
            }else{
                Object bean = createBean(beanName,beanDefinition);
                return bean;
            }
        }else{
            throw new NullPointerException();
        }




    }

}
