<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up - Expense Manager</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .container { background: white; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 400px; }
        h2 { text-align: center; color: #333; }
        .form-group { margin-bottom: 1rem; }
        label { display: block; margin-bottom: 0.5rem; color: #555; }
        input, select { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 0.7rem; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 1rem; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .login-link { text-align: center; margin-top: 1rem; }
        .login-link a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Your Company Account</h2>
        <form action="signup" method="post">
            <div class="form-group">
                <label for="fullName">Your Full Name</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Your Email (Admin)</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="companyName">Company Name</label>
                <input type="text" id="companyName" name="companyName" required>
            </div>
            <div class="form-group">
                <label for="country">Country</label>
                <select id="country" name="country" required>
                    <option value="">Loading countries...</option>
                </select>
            </div>
            <input type="hidden" id="currency" name="currency">
            <button type="submit">Sign Up</button>
        </form>
         <div class="login-link">
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const countrySelect = document.getElementById('country');
            const currencyInput = document.getElementById('currency');

            // Fetch countries and currencies from the API
            fetch('https://restcountries.com/v3.1/all?fields=name,currencies')
                .then(response => response.json())
                .then(data => {
                    countrySelect.innerHTML = '<option value="">Select a country</option>'; // Clear loading message
                    
                    // Sort countries alphabetically
                    data.sort((a, b) => a.name.common.localeCompare(b.name.common));

                    data.forEach(country => {
                        // We only care about countries that have currencies defined
                        if (country.currencies) {
                            const currencyCode = Object.keys(country.currencies)[0];
                            const option = document.createElement('option');
                            option.value = country.name.common;
                            option.textContent = country.name.common;
                            option.setAttribute('data-currency', currencyCode);
                            countrySelect.appendChild(option);
                        }
                    });
                })
                .catch(error => {
                    console.error('Error fetching countries:', error);
                    countrySelect.innerHTML = '<option value="">Could not load countries</option>';
                });

            // When a country is selected, update the hidden currency input field
            countrySelect.addEventListener('change', (e) => {
                const selectedOption = e.target.options[e.target.selectedIndex];
                const currency = selectedOption.getAttribute('data-currency');
                currencyInput.value = currency;
            });
        });
    </script>
</body>
</html>