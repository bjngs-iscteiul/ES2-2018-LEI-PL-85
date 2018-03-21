$('#topHeader').append($('<table cellspacing="30">\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="index.html">HOME</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="problemView.html">PROBLEM</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="faqView.html">FAQ</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="supportView.html">SUPPORT</a>\n' +
    '        </th>\n' +
    '    </table>'));



$('#bottomFooter').append($('<table cellspacing="30">\n' +
    '        <tr>\n' +
    '            <th><h2>About the tool</h2></th>\n' +
    '            <th><h2>FAQ</h2></th>\n' +
    '            <th><h2>Legal</h2></th>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <th><a class="footerLinks">Why use it?</a></th>\n' +
    '            <th><a class="footerLinks">Most asked questions</a></th>\n' +
    '            <th><a class="footerLinks">Privacy</a></th>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <th><a class="footerLinks">How it works?</a></th>\n' +
    '            <th><a class="footerLinks">How to add a problem</a></th>\n' +
    '            <th><a class="footerLinks">Terms of service</a></th>\n' +
    '        </tr>\n' +
    '        <tr>\n' +
    '            <th><a class="footerLinks">Explore more about</a></th>\n' +
    '            <th><a class="footerLinks">Can\'t upload JAR</a></th>\n' +
    '            <th><a class="footerLinks">Usage Plan Terms</a></th>\n' +
    '        </tr>\n' +
    '\n' +
    '    </table>'));


window.onscroll = function() {myFunction()};

var header = document.getElementById("topHeader");
var sticky = header.offsetTop;

function myFunction() {
    if (window.pageYOffset >= sticky) {
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}

