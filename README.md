# project Autyzmsoft_POP
Automatic tests of some functionalities of https://autyzmsoft.pl website
<p>
Most convenient usage:&nbsp;&nbsp;<em><strong>mvn clean test</strong></em><br>
Html reports are placed in <em><strong>target/surfire-reports/index.html</strong></em> in the default directory
</p>

<h2>List of test cases:</h2>
Totals: 12 test cases<br>
<em>HP stands for Home Page Tests, DP stands for Download Page test,<br>
FV - Full Versions Page test, OD - Order Details Page test</em>
<hr>
HP_01<br>
<strong>public void testDownloadPageAppears(){...}</strong><br>
Checking whether Download Page appears.
<hr>
HP_02<br>
<strong>public void testFullVersionsPageAppears(){...}</strong><br>
Checking whether Full Versions Page appears.
<hr>
HP_03<br>
<strong>public void testAllLinksAreActive(){...}</strong><br>
Checking whether all links on the Home Page are active.<br>
<span style="color: red">Note: HP_03 is time-consuming</span>
<hr>
HP_04<br>
<strong>public void testLiczykropkaJsOpens(){...}</strong><br>
Test whether javascript application LiczyKropka opens<br>
Passed if:<br>
1. 5 buttons appear AND<br>
2. a big number appears<br>
<hr>
HP_05<br>
<strong>public void testClickingCorrectButtonInLiczykropkaJs(...){...}</strong><br>
Passed if:<br>
1. All buttons with numbers except the proper one(s) are disabled AND<br>
2. Big green button with @ sign appears<br>
<hr>
HP_06<br>
<strong>public void testProfMarcinJsOpens(){...}</strong><br>
Test whether javascript application profMarcin opens<br>
Passed if:<br>
1. 4 buttons appear AND<br>
2. a picture appear<br>
<hr>
HP_07<br>
<strong> public void testClickingCorrectButtonInProfmarcinJs(...){...}</strong><br>
Passed if:<br>
1. All texts on buttons with improper words are printed in 'font-weigh: normal'; text on button(s) with proper<br>
   word are printed in font-size > 100% AND<br>
2. There appear a text element under the picture. The element contains proper word AND<br>
3. Big green button with right arrow appears<br>
<hr>
DP_01<br>
<strong>public void testGettingDownloadLinksWithCorrectEmail(...){...}</strong><br>
Passed if:<br>
1. Text "WYSLANO LINKI NA ADRES" appears AND<br>
2. Address email appears as text on the screen. The address is the same as the address given in the form.<br>
<br>
<hr>
DP_02<br>
<strong>public void testGettingDownloadLinksWithIncorrectCorrectEmail(...){...}</strong><br>
Passed if:<br>
Text "We wprowadzonych danych wystąpiły błędy" appears<br>
<br>
<hr>
FV_01<br>
<strong>public void testClickOrderButtonsWithoutChoosingItems(...){...}
</strong><br>
Passed if Alert window appears<br>
ddt is used as there are 2 buttons that clicking on them should have same effect<br>
<hr>
FV_02<br>
<strong>public void testClickOrderButtonsAfterChoosingItems(...){...}
</strong><br>
Passed if Order Details page appears<br>
ddt is used as there are 2 buttons that clicking on them should have same effect<br>
<hr>
OD_01<br>
<strong>public void testIsAmountCorrectlyPassed(...){...}</strong><br>
Test whether the total amount from Order Details page is correctly passed to DotPay system.<br>
Passed if both amounts, the one from Order Detail and the one from DotPay, are equal.<br>
ddt is used to enforce running the test more than once.<br>
<p>&nbsp;</p>