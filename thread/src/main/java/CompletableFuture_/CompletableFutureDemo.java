package CompletableFuture_;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Auther: 徐少伟
 * @Date: 2020/8/3
 * @Description: CompletableFuture使用，多线程、异步、链式
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            completableFuture.complete("this is result");//另一个线程调用complete方法
        }).start();

        String result = null;
        result = completableFuture.get();//阻塞等待返回，抛出执行，中断异常
        System.out.println(result);

        System.out.println("===================================runAsync============================================");

        //runAsync 传入的是 Runnable 没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println("task is running");
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("end");
            }
        });
        completableFuture1.get();//阻塞等待完成

        System.out.println("=====================================supplyAsync=========================================");

        //传入的是Supplier类似callable，通过get方法获取返回值
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result";
        });
        result = completableFuture2.get();//阻塞等待完成
        System.out.println(result);

        System.out.println("======================================thenRun=========================================");

        //链式编程
        CompletableFuture<Void> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task is running");
            return "hello";
        }).thenRun(() -> {
            System.out.println("finish");
        });//无参数，无返回值 runnable

        System.out.println("========================================thenAccept=======================================");

        CompletableFuture<Void> completableFuture4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task is running");
            return "hello";
        }).thenAccept(o -> {
            System.out.println("finish + " + o);
        });//有参数，无返回值 (Consumer)

        System.out.println("=========================================thenApply======================================");

        CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task is running");
            return "hello";
        }).thenApply(o -> {
            System.out.println("finish + " + o);
            return "world";
        });//有参数，有返回值 (Function) 返回 CompletableFuture<String>
        System.out.println(completableFuture5.get());

        System.out.println("===================================anyOf============================================");


        completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("2 is running");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("2 is end");
            }
            return "result2";
        });

        completableFuture5 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("5 is running");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("5 is end");
            }
            return "result5";
        });

        CompletableFuture<String> completableFuture6 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("1 is running");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("1 is end");
            }
            return "result6";
        });

        //可变长度参数，可以传入多个completableFuture，哪个先完成就返回哪个的结果，剩下的结果会被丢弃，**但是都会完成**。
        CompletableFuture<Object> completableFuture7 = CompletableFuture.anyOf(completableFuture6, completableFuture2, completableFuture5);
        System.out.println((String) completableFuture7.get());

        System.out.println("======================================allOf=========================================");

        //测试allOf执行每一个，与操作

        CompletableFuture<String> completableFuture8 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("8 is running");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("8 is end");
            }
            return "result8";
        });

        CompletableFuture<String> completableFuture9 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("9 is running");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("9 is end");
            }
            return "result9";
        });

        CompletableFuture<String> completableFuture10 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("10 is running");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("10 is end");
            }
            return "result10";
        });


        CompletableFuture[] cs = new CompletableFuture[]{completableFuture8, completableFuture9, completableFuture10};

        //可变长度参数，可以传入多个completableFuture，最后结果返回值是Void，如果先要获取中间返回的结果
        CompletableFuture<Void> completableFuture11 = CompletableFuture.allOf(cs);

        //提交后，需要加入thenApply中进行后处理,获取所有的结果通过调用get方法
        CompletableFuture<List<String>> allresult = completableFuture11.thenApply(v -> {
            return Arrays.stream(cs).map(o -> {
                try {
                    return (String) o.get();//阻塞等待，将结果加入到list中
                } catch (Exception e) {
                    System.out.println("??");
                    return "";
                }
            }).collect(Collectors.toList());
        });

        CompletableFuture<String> completableFuture12 = allresult.thenApply(v -> {
            StringBuffer sb = new StringBuffer();
            v.forEach(o -> sb.append("--"+(String) o));//循环加入sb中，最后返回结果值
            return sb.toString();
        });

        /*
        //链式调用，结合起来就是
        CompletableFuture<String> completableFuture13 = CompletableFuture.allOf(cs).thenApply(v -> {
            return Arrays.stream(cs).map(o -> {
                try {
                    return (String) o.get();//阻塞等待，将结果加入到list中
                } catch (Exception e) {
                    System.out.println("??");
                    return "";
                }
            }).collect(Collectors.toList());
        }).thenApply(v -> {
            StringBuffer sb = new StringBuffer();
            v.forEach(o -> sb.append("--"+(String) o));//循环加入sb中，最后返回结果值
            return sb.toString();
        });
        */

        System.out.println("then we get" + completableFuture12.get());

        /*
        8 is running
        8 is end
        9 is running
        10 is running
        10 is end
        9 is end
        then we get--result8--result9--result10
         */

        System.in.read();

    }
}
