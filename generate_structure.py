import os

base_path = "helpdesk-api-backend/src/main/java/com/karan/helpdesk"

structure = {
    "controller": ["AuthController", "UserController", "TicketController"],
    "service": ["AuthService", "UserService", "TicketService"],
    "repository": ["UserRepository", "TenantRepository", "TicketRepository"],
    "entity": ["User", "Tenant", "Ticket"],
    "dto": ["UserDTO", "TicketDTO", "LoginRequest", "LoginResponse", "CreateUserRequest"],
    "security": ["JwtUtil", "JwtAuthenticationFilter", "SecurityConfig"],
    "tenant": ["TenantContext", "TenantFilter"],
    "config": ["AppConfig"]
}

def java_template(pkg, classname):
    return f"""package com.karan.helpdesk.{pkg};

public class {classname} {{
    // TODO: Implement {classname}
}}
"""

for pkg, classes in structure.items():
    dir_path = os.path.join(base_path, pkg)
    os.makedirs(dir_path, exist_ok=True)
    for classname in classes:
        filename = f"{classname}.java"
        filepath = os.path.join(dir_path, filename)
        with open(filepath, "w") as f:
            f.write(java_template(pkg, classname))

print("✅ Backend folder structure generated successfully.")
