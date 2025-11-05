#### Неизменяемые параметры запуска:
-XX:+HeapDumpOnOutOfMemoryError </br>
-XX:HeapDumpPath=./logs/heapdump.hprof </br>
-XX:+UseG1GC </br>
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m </br></br>


#### Время отработки при изменении параметров Xmx и Xms до оптимизации:
1) Xms256m Xmx256m: spend msec:70561, sec:70
2) Xms512m Xmx512m: spend msec:70601, sec:70
3) Xms1024m Xmx1024m: spend msec:68874, sec:68
4) Xms2048m Xmx2048m: spend msec:65615, sec:65
6) Xms4096m Xmx4096m: spend msec:70270, sec:70
7) Xms128m Xmx128m: spend msec:68571, sec:68
8) Xms32m Xmx32m: spend msec:72068, sec:72
9) Xms16m Xmx16m: spend msec:124204, sec:124
10) Xms8m Xmx8m: spend msec:407307, sec:407

Вывод оптимальный размер хипа на моей ПК для не оптимизированного приложения 32mb, т.к. при 16mb уже начинает часто работать не только "Pause Young"
и время работы приложения заметно проседает. При увеличении хипа GC срабатывал в разы реже, но как можно наблюдать в статистике, 
сильного влияния на производительность при этом нет. </br></br>


#### Время отработки при изменении параметров Xmx и Xms после оптимизации:
1) Xms8m Xmx8m: spend msec:42483, sec:42
2) Xms16m Xmx16m: spend msec:33324, sec:33
3) Xms32m Xmx32m: spend msec:31122, sec:31
4) Xms128m Xmx128m: spend msec:30988, sec:30
5) Xms1024m Xmx1024m: spend msec:30988, sec:30

Вывод оптимальный размер хипа на моей ПК для оптимизированного приложения 16mb, т.к. при 8mb уже начинает часто работать не только "Pause Young"
и время работы приложения не много проседает.
Также конечно заметно на сколько быстрее работа приложения после отпимизации. С примитвными типами время сократилось примерно в 2 раза.