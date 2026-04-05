#ECommerce app
-I am learning springboot and this project is my learning ground. I add features as I learn more on springboot

##Feature
-UserController
    -registerUser API EndPoint
        -Takes RegisterUserDto as argument
            -Properties: firstName, lastName, email, username, password
        -Returns UserDto
            -Properties: id, userName, fullName, email
-ProductController
    -showAllProducts API EndPoint
        -Takes RequestParam of categoryId (Optional) as argument
        -Returns List<ProductDto>
            -Properties: id, name, description, price
-CartController
    -addToCart API EndPoint
        -Takes AddCartItemDto as argument
            -Properties: userId, productId, quantity
        -Returns CartItemDto
            -Properties: productId, productName, productDescription, quantity, price, totalPrice
    -showCart API EndPoint
        -Takes RequestParam userId as argument
        -Returns CartDto
            -Properties: List<CartItemDto>, totalGrandPrice
    -deleteFromCart API EndPoint
        -Takes RequestBody of DeleteCartItemDto
            -Properties: userId, productId