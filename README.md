# Educational Organizer
This product was developed by:
- Lars Martin Aders
- Tørres Lande
- Henning Bang Halvorsen
- Adrian Junger Steffenakk
---
### Running the software
1. Download the project as a zip file and open it in your favourite unzipper (7zip, WinZip, WinRar etc).
2. Unzip the file and save the folder in a place you can easily locate.
3. **(If you are connected to a network at NTNU, you can skip this step)** Connect to the NTNU network using the Cisco AnyConnect VPN.
 ```see [this link][1] for more info on how to do this. ```
4. **(This might work the same in other IDEs, but this setup was based on IntelliJ IDEA)** Open IntelliJ IDEA and select "Open".
5. Locate the folder you unzipped earlier. Select it and hit "OK".
6. Locate the ```Main.java``` class in the ```layout``` package. Try to run it by right-clicking and selecting ```Run Main.java```.
7. Most likely it will not work due to IntelliJ not detecting the external libraries we use.
8. Go to ``File -> Project Structure``. Under the ``Sources`` tab, make sure the files are marked as follows:
    - ``src`` as a ``Source Folder``
    - ``tests`` as a ``Test Folder``
    - ``resources`` as a ``Resource Folder``

    Eventually mark ``out`` as an ``Excluded Folder``, should it be present.
9. Go over to the ``Dependencies`` tab, and you will most likely see nothing or just some of the dependencies. Hit the little ``+`` icon to the right, and click ``JARs or directories``.
10. Locate the ``lib`` folder in the project files (if they are not present, wait a little while and then come back to this stage). Mark the all and click ``OK``.
11. They should now all be added, but not checked. Make sure that every single one of them is checked, and hit ``Apply``. Close the ``Project Structure`` window.
12. Try to run ``Main.java`` once again, and you should get the program running.
13. Should you run into any more trouble, feel free to contact us at henning.b.halvorsen@gmail.com and we will aid you as best we can!

___
We hope you enjoy this product, and that it helps you get organized and educated!

Thank you,

Educational Organizer team

[1]: https://innsida.ntnu.no/wiki/-/wiki/English/Install+VPN/