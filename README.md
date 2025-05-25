# Tread pool

## Параметры настройки пула

- corePoolSize
- maxPoolSize
- keepAliveTime и timeUnit
- queueSize
- minSparseThreads


## Обработка отказов
При переполнении очереди новая задача не берется в обработку.
Выбран данный подход, так как при нехватке ресурсов невозможно штатно обрабатывать такие ситуации.
Единственное решение проблемы - масштабирование и увеличение мощностей.

## Распределение задач
Задача поступает на случайную очередь. Данный подход выбран для простоты реализации и в среднем должен
давать тот же результат, что и при Round Robin, так как все равно присутствует элемент случайности при поступлении
задач в очереди - они могут быть разной сложности.

В качестве предпочтительного способа балансировки отмечу Least Connections.

## Кастомизация компонентов

- Разработана фабрика для создания потоков
- В качестве очередей задач используется массив BlockingQueue 

## Worker

- Обрабатывает задачи из закрепленной за ним очереди
- При отсутствии задач в течении заданного времени завершается, если общее число потоков превышает минимальное
- Перед выполнением задачи проверяет, что пул не находится в состоянии завершения

## Логирование

- При создании потока
- При завершении потока
- При поступлении задачи
- Если задача отклонена
- При выполнении задачи
- При отсутствии задач для worker в течение определенного времени


## Демонстрация программы
```
мая 25, 2025 12:29:28 PM org.example.AppThreadFactory newThread
INFO: Created new thread: Thread-1
мая 25, 2025 12:29:28 PM org.example.AppThreadFactory newThread
INFO: Created new thread: Thread-2
мая 25, 2025 12:29:28 PM org.example.AppThreadPool <init>
INFO: Created pool with 2 threads
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadFactory newThread
INFO: Created new thread: Thread-3
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: New worker created
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadFactory newThread
INFO: Created new thread: Thread-4
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: New worker created
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:28 PM org.example.AppThreadPool execute
INFO: Task added to queue

Some tasks
мая 25, 2025 12:29:29 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:29 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:29 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:30 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:30 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:31 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:31 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:32 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:33 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:35 PM org.example.AppWorker run
INFO: Task completed

Many tasks
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@14514713
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@69663380
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@5b37e0d2
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@4459eb14
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@5a2e4553
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@28c97a5
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@6659c656
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@6d5380c2
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@45ff54e6
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@2328c243
мая 25, 2025 12:29:43 PM org.example.AppThreadPool execute
INFO: Task added to queue
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@bebdb06
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@7a4f0f29
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@45283ce2
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@2077d4de
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@7591083d
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@77a567e1
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@736e9adb
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@6d21714c
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@108c4c35
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@4ccabbaa
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@4bf558aa
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@2d38eb89
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@5fa7e7ff
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@4629104a
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@27f8302d
мая 25, 2025 12:29:43 PM org.example.AppThreadPool logRejectedTask
WARNING: Task rejected: org.example.App$$Lambda$40/0x0000000800c041f0@4d76f3f8
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:44 PM org.example.AppWorker run
INFO: Task completed
мая 25, 2025 12:29:58 PM org.example.AppThreadPool shutdown
INFO: Shutdown
```

## Преимущества по сравнению со стандартной реализацией

- Несколько очередей
- Распределение задач
- Детальное управление потоками

В результате чего большее число задач имеет статус "Выполнена"

## Параметры пула для максимальной производительности

### corePoolSize
~ Число ядер CPU

### maxPoolSize
corePoolSize * 2

### queueSize
maxPoolSize * 2

### keepAliveTime
~ 10 секунд
