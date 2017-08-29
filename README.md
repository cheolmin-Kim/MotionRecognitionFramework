# Motion Recognition Framework Using various sensors

## 1. **Developers**

the following is a list of developers.

1. [**Chelmin - Kim**](https://github.com/cheolmin-Kim)  **\( kcm7582@naver.com \)**

2. [**Jimin - Kim**](https://github.com/SmileJM)  **\( lovelyeu@naver.com \)**

3. [**Hwasung - Seo**](https://github.com/Marsseo)  **\( ghktjddl3@naver.com \)**

4. [**Dongju - Jang**](https://github.com/Jdongju)  **\( dj9110@naver.com\)**

## 2. Overview

This framework has the following advantages.

1. 손쉽게 임베디드 장치와 CoAP 통신을 할 수 있는 환경을 제공한다.

2. 모션 인식 개발에 활용 가능한 알고리즘이 포함되어 있다.

3. 모션 프레임워크를 이용하는 사용자를 위해 Tutorial이 만들어져 있으며 Javadoc을 홈페이지에 기재 하여 놓았다.

4. 사용자의 환경에 맞게 조건을 변경하여 사용 가능 하도록 하였고 Overriding하여 직접 모션 알고리즘을 넣을 수 있도록 하였다.

5. Gyroscope Sensor의 특성상 yaw축에 Drift 현상이 발생하는데, 이럴 경우에도 모션인식이 가능하도록 솔루션을 제공한다.

6. 해당 프레임 워크의 전용 홈페이지를 개설하여 모션 인식 관련한 활발한 커뮤니티가 이루어 질 수 있도록 하였다.

7. 웹 사이트에서 클라이언트인 임베디드 장치에 연결된 센서들의 현재 상태를 체크 해볼 수 있도록 해준다.

## 3. What is this?

#### **▶ 설명**

It is Motion framework for remote control of drones, rovers, and various IoT devices in motion using sensor values.

This framework basically enables motion recognition using values of gyroscope sensor, ultrasonic sensor, acceleration sensor, infrared sensor, and button sensor.

* Motion recognition using axis values.

![](http://blogfiles8.naver.net/MjAxNzA4MjBfMTU5/MDAxNTAzMTYwMTExNTk4.mWaDX1DZ4RmC70xyFR3iSviHcJFThplv7dVib9UiT9kg.wCmswrKRV5WMpa9tKij9jbQLWg6Pp21-46BzD-cPnqMg.PNG.sword97/yaw.png)

임베디드 장치에서 자이로센서와 가속도 센서를 활용하여 yaw축 , pitch축, roll축의 현재 각도만 실시간으로 전송해 주면 우리 프레임워크를 사용하여 개발한 Server측에서 모션을 인식할 수 있다. 만약 자이로스코프와 가속도 센서를 사용하여 계산해 낸 각도를 활용한 모션인식을 하고자 한다면 모션 트리거가 되어 줄 적어도 하나 이상의 다른 센서가 필요하다. 모션 트리거는 모션 행동의 시작과 끝을 알려주는 기능을 한다. 다른 센서가 모션 트리거가 되어 트리거가 On되고 Off되는 시점 사이에 취해진 모션이 인식 되어진다.

* Motion recognition using distance values.

센서로 측정한 거리를 우리 프레임 워크를 사용하여 개발한 Server 측으로 전송해 주게 되면 거리 값에 따라 이벤트를 줄 수 있도록 하였다. 거리를 측정하기 위해 사용될 수 있는 센서는 초음파 센서 , 적외선 센서 등이 있다

* Motion recognition using On / off status

센서의 On/ Off 상태를 프레임 워크를 사용하여 개발한 Server 측으로 String으로 전송해 주게 되면 상태 값에 따라 이벤트를 줄 수 있도록 하였다. 상태를 측정하기 위해 사용될 수 있는 센서는 터치 센서 , 버튼 센서 등이 있다.

#### ▶ 프로세스

이 프레임 워크의 동작 프로세스는 3단계로 나누어 집니다.

* 1단계는 모션 트리거가 되지 않은 상태입니다.

* 2단계는 모션 트리거가 On 되어진 상태 입니다.

* 3단계는 모션 트리거가 Off 되어지고 인식된 모션의 결과가 나오는 단계입니다.

## 4. Usage

#### ▶ Client

![](http://postfiles3.naver.net/MjAxNzA4MjRfMjI4/MDAxNTAzNTUxMTk4NDU0.6HMlCvUMVpZbabsHB8dsP5tIf0genckfsjrMEVKqIdUg.IZT9nBJVeipVqRefdKnX4nOJe5YIwtbm8ZYS4JjgX84g.PNG.kcm7582/그림1.png?type=w3)

위의 그림과 같이 임베디드 장치를 손이나 팔 위에 올려놓고 팔을 앞으로 뻗었을 때를 기본 상태로 간주한다. 해당 상태가 되었을 때 yaw축 , roll축 , pitch축 각각은 180도가 되도록 하고 각각의 각의 범위는 0도에서 360도가 되도록 클라이언트에서 셋팅하여 서버로 값을 전송해야 한다. yaw축은 0도에서 360도 까지의 범위만 유지하고 정확히 180도가 되지 않더라도 모션 인식이 동작하는데 무리 없도록 하는 솔루션을 제공한다. 따라서 yaw축에 Drift 현상이 발생 하더라도 모션을 인식하는 것에 큰 지장을 주지 않도록 하였다.

클라이언트에서 전송하는 각각의 값은 서로에게 영향을 주지 않고 독립적인 상태였을 때 가장 이상적인 모션 인식 알고리즘을 만들 수 있다. 따라서 클라이언트 측에서 되도록 신경써서 값을 독립적이며 정확하게 보내 주도록 하는 것을 권장한다.

프레임 워크에서는 CoAP 통신이 가능하도록 Server 환경이 갖추어져 있으며 따라서 클라이언트 측에서는 해당 리소스로 센서의 값을 전송만 해주면 된다.

* 3축의 값을 전송받는 리소스 이름은 gyroscope 이며 post 방식으로 {"sensor":"gyroscope","yawAngle":"value","pitchAngle":"value","rollAngle":"value"}  라는 Json 형태의 데이터를 보내줘야 한다.

또한 , post방식으로 쿼리 스트링 형태로 데이터를 전송 해도 된다.

sensor=gyroscope&yawAngle=value&pitchAngle=value&rollAngle=value 와 같이 각 축의 value 값을 보내줘야 한다.

* 거리 값을 입력 받는 리소스 이름은 infraredray이며 post 방식으로 {"sensor":"infraredray","distance":"value"} 의 형태로 Json 데이터를 보내줘야 한다. 또 다른 거리 값을 입력 받는 리소스 이름은 ultrasonic 이며 post 방식으로 {"sensor":"ultrasonic","distance":"value"} 의 형태로 Json 데이터를 보내줘야 한다.

* On/Off 값을 입력 받는 리소스 이름은 infraredray이며 post 방식으로 {"sensor":"infraredray","status":"status"} 의 형태로 Json 데이터를 보내줘야 한다. status의 value 값은 해당 모션의 상태를 보내게 되며 on이나 off를 보내주면 된다. 또한, get 방식으로 쿼리 스트링을 전송 할 수 있다. sensor=button&status=status 의 형태로 status의 value 값으로 센서 상태\(on,off\)를 보내주면 된다.

![](http://postfiles6.naver.net/MjAxNzA4MjRfMTY1/MDAxNTAzNTUxMTk2Nzkz.9PdWhNX6KcUbFuRaH2DSaCXm71CEBbegNFPaXw9fl3cg.fYwcO1_qrQUjnebT9e4FctjF9o4W5PZxmJzw_6KMBJ4g.PNG.kcm7582/image.png?type=w3)

우리 Repository의 Client4MR 프로젝트는 테스트를 위해 임베디드 장치에서 동작하는 client를 만든 것이다.

이 프로젝트는 아래 그림과 같이 각도 값을 보내주고 있으며 post 방식으로 보내주고 있다.

자세한 것은 Client4MR 프로젝트를 참고하면 될 것이다.

우리 Repository의 CoapClient\_Android 프로젝트에는 휴대폰의 센서를 이용하여 3개의 축을 보내주는 안드로이드 어플의 코드가 있다. 해당 어플을 휴대폰에 설치하고 동작해 보면 아래와 같은 어플 화면을 볼 수 있다.

![](http://postfiles14.naver.net/MjAxNzA4MjRfMTc2/MDAxNTAzNTUxMTk2OTYw.NOGB_zKrjMr4KMkiRQFY9Y9I62ph4UEMcivVBJIHTQUg.-JICB9QU4pD8GK4o24RJ05g2Td_zStgo_Fdakij_7N4g.PNG.kcm7582/image2.png?type=w3)

프레임워크를 활용하여 만든 CoAP 서버가 동작하는 장치의 IP를 입력하고 IP 입력 버튼을 눌러주면 해당 장치로 3축의 각도 값을 실시간으로 보내주게 된다. Trigger 버튼을 누르게 되면 트리거를 On/Off 시킬 수 있고, 각도를 활용한 모션인식을 했을 때 인식된 모션의 이름이 화면에 뜨도록 하였다.

#### ▶ Tutorial

![](http://postfiles4.naver.net/MjAxNzA4MjRfMTEy/MDAxNTAzNTUxMTk3NDQ1.QKRAaELT6TAN6l6aOpJVx_vtmnxiUAnoZD3cwqvS6_4g.dFadd-hEnqKtSVzL6lraqemURiQ_oLBUX5GVmUHBXIAg.PNG.kcm7582/image4.png?type=w3)

최종으로 인식된 모션 값과 실행하고자 하는 코드를 매칭 시켜주는 ActionInterface를 구현하고 있는 Action 클래스를 생성해주고 Recognizer 클래스를 생성하면서 매개변수로 넘겨준다.

Action 클래스를 만들면서 재정의 된 메소드로 넘어 오는 매개변수는 String 타입으로 모션의 이름이 될 것이다. 해당 모션과 매칭 하고자 하는 동작을 작성해 주면 된다.

![](http://postfiles1.naver.net/MjAxNzA4MjRfMTMx/MDAxNTAzNTUxMTk3MzA3.OgMLe2eaz2Wgep8J2_Ft8d_chuaSdysUmZb6DsnOw0wg.WNa5kPyt3N5J1wt7Rj6DNSc6vz7JtVbf0KpZH9RD1I4g.PNG.kcm7582/image3.png?type=w3)

이 프레임 워크를 사용하면 쉽게 Coap 통신을 할 수 있다. 아래 코드 와 같이 Recognizer 클래스에 있는 start\(\) 메소드를 실행시켜 주는 것으로 Coap 서버가 실행된다.

이후에 기존에 모션 트리거가 실행되지 않은 1단계 부분에서 동작될 메소드를 설정해 줄 수 있다.예를들면, 트리거를 On 시킬 코드나 다른 센서 활용 코드, 동작하길 원하는 코드들을 작성 할 수 있다.

![](http://postfiles1.naver.net/MjAxNzA4MjRfMTI2/MDAxNTAzNTUxMTk3NTc2.qLQ-dbx0JgljAdLbchrqDq56DnR-bOTmQmStj0rMdogg.b8TlJzIoO3zOZM8AvkE9FzHXNFpO5pDZfn_0m5SpunIg.PNG.kcm7582/image5.png?type=w3)

![](http://postfiles4.naver.net/MjAxNzA4MjRfMTgy/MDAxNTAzNTUxMTk3NzQ3.gX0QH3gEsZkN9sqAY2E6Yb1cF2vuSL03Wsvp9p82qP0g.HBZHTdNZ2YmEByRudh8LQNLT0x70Y017lWFPgo4mw-cg.PNG.kcm7582/image6.png?type=w3)

위 코드와 같이 MotionCheck.triggerOnMotionList에 TriggerMotionInterface를 구현한 클래스를 생성하여 추상 메소드를 재정의 한후에 사용하고 싶은 타입의 메소드만 적어주고 사용하지 않는 메소드는 비어있도록 놔두면 된다.

위와같은 코드를 작성 하지 않으면 미리 설정해 둔 세가지 메소드가 동작하게 될것이다.

디폴트로 설정되어 있는 조건은 아래와 같다.

* gyroscope리소스로 전송된 pitch 축의 각도가 130도 이하가 되었다가 170도 이상으로 되었을 때 트리거가 On 된다.
* button 리소스로 전송된 button 상태가 On로 되었을때 트리거가 On된다.
* infraredray 리소스로  전송된 거리 값이 10 이하가 되었을 때 트리거가 On 된다.

모션 트리거가 On 되고 난 이후 2단계로 전환 되었을 때 동작될 메소드를 설정해줄수 있다. 예를 들면, 트리거를 off 시킬 메소드나 다른 센서 활용 코드,동작하길 원하는 코드들을 작성 할 수 있다.

![](http://postfiles15.naver.net/MjAxNzA4MjRfMTY5/MDAxNTAzNTUxMTk3ODgy.ONKTqJHNydIOk-Ctk1CCCEZDmCtbTZtygvFR4eZTZ3wg.3MxJ7bSPWgoCsrJf5JMrmN2sN2UTQhz8uMHa89WQ4M0g.PNG.kcm7582/image7.png?type=w3)

위 코드와 같이 MotionCheck.triggerOffMotionList에 TriggerMotionInterface를 구현한 클래스를 생성하여 추상 메소드를 재정의 한후에 사용하고 싶은 타입의 메소드만 적어주고 사용하지 않는 메소드는 비어있도록 놔두면 된다.

위와같은 코드를 작성 하지 않으면 미리 설정해 둔 세가지 메소드가 동작하게 될것이다.

디폴트로 설정되어 있는 조건은 아래와 같다.

* gyroscope리소스로 전송된 pitch 축의 각도가 130도 이하가 되었다가 170도 이상으로 되었을 때 트리거가 Off 된다.
* button 리소스로 전송된 button 상태가 Off로 되었을때 트리거가 Off된다.
* infraredray 리소스로  전송된 거리 값이 10 이하가 되었을 때 트리거가 Off 된다.

트리거가 off 되고 난 이후 동작할 3단계에서는 모션을 인식하는 과정이 진행 되는데 여기서 사용자가 직접 모션을 인식할 수 있는 알고리즘을 작성하여 동작하도록 할 수 있다.

우선 각도의 변화량을 확인하기를 원하는 각도의 구간을 정하고 해당 각도의 구간 안에서 변화되는 각도의 변화량의 값의 범위를 리스트에 입력하여 캐치할 수 있도록 한다.

![](http://postfiles13.naver.net/MjAxNzA4MjRfMTc5/MDAxNTAzNTUxMTk4MDI4.nqUePvPi_v12ZUU1A22sH1up21jz71U6Gx8-wWBOhbQg.8xjqQLF-bKGxdfacyzi4Cqf6p7-b7rRu_8MBO3738GYg.PNG.kcm7582/image8.png?type=w3)

위의 이미지와 같이 배열 형태로 \[yaw min\(0\), yaw max\(1\), roll min\(2\) , roll max\(3\),pitch min\(4\),pitch max\(5\), yaw Gap Min\(6\),yaw Gap Max\(7\),roll GapMin \(8\),roll Gap Max \(9\),pitch Gap Min \(10\), pitch Gap Max\(11\)\]  
 과 같이 12개의 숫자를 입력해 준다. 이때 해당 사항을 고려하지 않고 캐치 하고 싶을 경우 max와 min에 각각 0을 넣어주면 된다.

이렇게 입력된 조건을 고려하여 모션 트리거 ON과 Off 사이에 전송받은 각각의 축들의 값을 확인하여 만약 조건을 만족하면 각도의 변화량이 크기가 4인 difference 배열 안에 입력되어 진다. 이때 조건을 만족하지 않으면 0의 값이 입력되어져 나온다.

difference 배열은 0번 인덱스에 해당 각도가 들어온 순서가 입력되며, 1번 인덱스에 yaw축 각의 변화량, 2번 인덱스에 roll축 각의 변화량, 3번 인덱스에 pitch축 각의 변화량이 입력되어진다. 이 difference 배열은 트리거가 on, off되었을 때 사이에 전송받은 횟수 만큼 만들어져 나중에 사용되어 진다. 이 값들은 differenceResultList에 yawRollPitchRangeList의 인덱스 번호와 동일한 인덱스 번호로 differenceResultList에 difference를 담고있는 리스트로 추가되어져 나온다. 따라서 모션을 만드는 알고리즘을 넣고 싶다면 조건을 입력해 놓은 yawRollPitchRangeList의 인덱스와 동일한 인덱스 값을 가진 differenceResultList의 엘리먼트를 가져오면 된다. 이 엘리먼트는 difference 배열을 담고있는 리스트 형태일 것이고, 해당 리스트의 각각의 엘리먼트들인 difference 배열을 꺼내어 필요한 값을 꺼내어 사용하면 된다. 이와 같은 작업은 GyroMotionInterface를 구현한 클래스를 생성하여 gyroMotion\(List&lt;List&gt; differenceResultList, Map&lt;String, Integer&gt; motionMap\) 안에서 해주면 된다. 이때 캐치 하고자 하는 값들을 잡을 수 있는 알고리즘을 작성하고 캐치한 값들의 수를 세어준다. 이렇게 다 세어진 수들은 해당 메소드의 파라미터인 Map안에 모션이름을 키값으로 하고 총 세어진 카운트 값을 Value로 하여 넣어주면 된다. 아래 코드를 참고하면 될것이다.

![](http://postfiles14.naver.net/MjAxNzA4MjRfMjU5/MDAxNTAzNTUxMTk4MzI0.9Xxf1C4VpyWgeMaRXec8-Y6Ih16v7yuV6ZCjnHrXyd4g.CsxgcQMy2WzMvVpp2ukb3kpewD5QyHJwh_ncarVdu3Ug.PNG.kcm7582/image9.png?type=w3)

만약 yawRollPitchRangeList를 다 비우고 위와 같이 모션을 추가해주는 과정을 하지 않는다면 프레임 워크에 기본으로 탑재되어 있는 모션들이 인식되어 질 것이다. 디폴트로 설정되어 있는 모션들은 다음과 같다.

* 위
* 아래
* 왼쪽
* 오른쪽
* Zigzag 모션
* N 모션
* Pitch축 오른쪽 회전
* Pitch축 왼쪽 회전
* V모션

## 5. Requirements

* 프레임 워크를 동작하기 위하여 함께 추가해야 할 jar 파일들

![](http://postfiles12.naver.net/MjAxNzA4MjRfMjU0/MDAxNTAzNTUyMDEwNjU4.MBzmvnkJSJYW0usjKI4e-GGBqDBUghHTYUWVdpkKJnwg.quYl3zr3_0GtsFs8dR0zBRryTTj6pqqBetkDUE0gr1Ug.PNG.kcm7582/1.png?type=w3)

![](http://postfiles13.naver.net/MjAxNzA4MjRfMjA5/MDAxNTAzNTUyMDEwODE5.1Is6udQDZff6DjZATLgM5ucvOz7EcKekHn2fQUWqHOkg.vcF8nwT__YZFGwn7VC_g1Mz9raQ2w1UzlCq16npCarMg.PNG.kcm7582/2.png?type=w3)

![](http://postfiles6.naver.net/MjAxNzA4MjRfMTE2/MDAxNTAzNTUyMDEwOTU1.Ei76Br3szDFyXpCwzbjM1bkf_k8lzQKXlYQJCnKtXjYg.vhl21WjM9yIJHIAN4NuCzD6CXnUR6WvhFxBV7IMI7YQg.PNG.kcm7582/3.png?type=w3)

## 6. Download

[여기를 눌러주세요.](http://blog.naver.com/kcm7582/221080544867)

링크클릭-&gt;  해당 블로그로 이동 -&gt;  첨부파일을 다운로드

