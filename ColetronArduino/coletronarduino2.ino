#include <Servo.h>

Servo myservo;
int servoPin = 9;

void setup() {
    myservo.attach(servoPin);
    myservo.write(90); // Inicia o servo na posição 0 graus
    Serial.begin(9600);
}

void loop() {
    if (Serial.available() > 0) {
        char comando = Serial.read();
        if (comando == 'A') {
            myservo.write(0); // Gira o servo para 90 graus
        } else if (comando == 'B') {
            myservo.write(90); // Retorna o servo para 0 graus
        }
    }
}