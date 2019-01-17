FROM java:8
COPY . /app
WORKDIR /app/build
RUN echo "sapgw00  3300/tcp \nsapmsNPL  3601/tcp" >> ../../etc/services
EXPOSE 3300
EXPOSE 3601
CMD java -Djava.library.path=/app/build/libs/libsapjco3.so -jar libs/IdocDemo-1.0.jar