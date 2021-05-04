<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    </head>
<body>

    <p>Hello</p>

    <div id="like_button_container"></div>


    <footer class="footer">
        <p>&copy;&nbsp;Masaryk University</p>
    </footer>

    <!-- Load React. -->
    <!-- Note: when deploying, replace "development.js" with "production.min.js". -->
    <script src="https://unpkg.com/react@17/umd/react.development.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@17/umd/react-dom.development.js" crossorigin></script>
    <script src="https://unpkg.com/babel-standalone@6.15.0/babel.min.js"></script>


    <!-- Load our React component. -->
    <script src="/pa165/index.js"></script>



    <script type="text/babel">
        class Hello extends React.Component {
            render() {
                return <h1>Hello World!</h1>
            }
        }

        ReactDOM.render(<Hello />, document.getElementById('mydiv'))
    </script>

    <div id="test"></div>

</body>
</html>