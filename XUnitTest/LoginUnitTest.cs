using AutoMapper;
using Bodeguin.Application.Communication.Response;
using Bodeguin.Application.Security.Encript;
using Bodeguin.Application.Service;
using Bodeguin.Domain.Entity;
using Bodeguin.Infraestructure.Context;
using Bodeguin.Infraestructure.Repository;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;
using Microsoft.Extensions.Options;
using Bodeguin.Application.Security.JsonWebToken;
using Bodeguin.Application.Communication.Request;

namespace XUnitTest
{
    public class LoginUnitTest
    {
        [Fact]
        public async void TestSignInSuccess()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)))
            );

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var fakeUser = new User()
            {
                Id = 1,
                CreateAt = DateTime.Now,
                Direction = "Direction",
                Dni = "72183382",
                Email = "danieljimenezcanales@gmail.com",
                FirstLastName = "Jimenez",
                SecondLastName = "Canales",
                IsActive = true,
                IsAdmin = false,
                ModifiedAt = DateTime.Now,
                Name = "Daniel",
                Password = "iccnm3vwVVtzoiVLj6eLTw=="
            };

            await unitOfWork.UserRepository.AddAsync(fakeUser, null);
            await unitOfWork.SaveChangesAsync();

            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeLogin = new LoginRequest()
            {
                Email = "danieljimenezcanales@gmail.com",
                Password = "123456789"
            };

            var result = await loginService.SignIn(fakeLogin);
            Assert.True(result.Valid);
            Assert.NotNull(result.Data);
        }

        [Fact]
        public async void TestSignInWrongUser()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)))
            );

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var fakeUser = new User()
            {
                Id = 1,
                CreateAt = DateTime.Now,
                Direction = "Direction",
                Dni = "72183382",
                Email = "danieljimenezcanales@gmail.com",
                FirstLastName = "Jimenez",
                SecondLastName = "Canales",
                IsActive = true,
                IsAdmin = false,
                ModifiedAt = DateTime.Now,
                Name = "Daniel",
                Password = "iccnm3vwVVtzoiVLj6eLTw=="
            };

            await unitOfWork.UserRepository.AddAsync(fakeUser, null);
            await unitOfWork.SaveChangesAsync();

            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeLogin = new LoginRequest()
            {
                Email = "usuario@gmail.com",
                Password = "123456"
            };

            var result = await loginService.SignIn(fakeLogin);
            Assert.False(result.Valid);
            Assert.Equal(1, result.ErrorCode);
            Assert.Null(result.Data);
        }

        [Fact]
        public async void TestSignInWrongPassword()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)))
            );

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var fakeUser = new User()
            {
                Id = 1,
                CreateAt = DateTime.Now,
                Direction = "Direction",
                Dni = "72183382",
                Email = "danieljimenezcanales@gmail.com",
                FirstLastName = "Jimenez",
                SecondLastName = "Canales",
                IsActive = true,
                IsAdmin = false,
                ModifiedAt = DateTime.Now,
                Name = "Daniel",
                Password = "iccnm3vwVVtzoiVLj6eLTw=="
            };

            await unitOfWork.UserRepository.AddAsync(fakeUser, null);
            await unitOfWork.SaveChangesAsync();

            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeLogin = new LoginRequest()
            {
                Email = "danieljimenezcanales@gmail.com",
                Password = "123456"
            };

            var result = await loginService.SignIn(fakeLogin);
            Assert.False(result.Valid);
            Assert.Null(result.Data);
            Assert.Equal(2, result.ErrorCode);
        }

        [Fact]
        public async void TestSignUpUserAlreadyExist()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg =>
            {
                cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)));
                cfg.
                CreateMap<SignUpRequest, User>()
                .ForMember(u => u.Email, u => u.MapFrom(s => s.Email))
                .ForMember(u => u.Password, u => u.MapFrom(s => Encript.EncriptText(s.Password)))
                .ForMember(u => u.Name, u => u.MapFrom(s => s.Name))
                .ForMember(u => u.FirstLastName, u => u.MapFrom(s => s.FirstLastName))
                .ForMember(u => u.SecondLastName, u => u.MapFrom(s => s.SecondLastName));
            });

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var fakeUser = new User()
            {
                Id = 1,
                CreateAt = DateTime.Now,
                Direction = "Direction",
                Dni = "72183382",
                Email = "ccarlin@gmail.com",
                FirstLastName = "Jimenez",
                SecondLastName = "Canales",
                IsActive = true,
                IsAdmin = false,
                ModifiedAt = DateTime.Now,
                Name = "Daniel",
                Password = "iccnm3vwVVtzoiVLj6eLTw=="
            };

            await unitOfWork.UserRepository.AddAsync(fakeUser, null);
            await unitOfWork.SaveChangesAsync();

            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeSignUp = new SignUpRequest()
            {
                Email = "ccarlin@gmail.com",
                Password = "123456",
                Name = "Javier",
                FirstLastName = "Del Piero",
                SecondLastName = "Rodriguez"
            };

            var result = await loginService.SignUp(fakeSignUp);
            Assert.False(result.Valid);
            Assert.Null(result.Data);
            Assert.Equal(3, result.ErrorCode);
        }

        [Fact]
        public async void TestSignUpUserNotCorrect()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg =>
            {
                cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)));
                cfg.
                CreateMap<SignUpRequest, User>()
                .ForMember(u => u.Email, u => u.MapFrom(s => s.Email))
                .ForMember(u => u.Password, u => u.MapFrom(s => Encript.EncriptText(s.Password)))
                .ForMember(u => u.Name, u => u.MapFrom(s => s.Name))
                .ForMember(u => u.FirstLastName, u => u.MapFrom(s => s.FirstLastName))
                .ForMember(u => u.SecondLastName, u => u.MapFrom(s => s.SecondLastName));
            });

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var fakeUser = new User()
            {
                Id = 1,
                CreateAt = DateTime.Now,
                Direction = "Direction",
                Dni = "72183382",
                Email = "ccarlin@gmail.com",
                FirstLastName = "Jimenez",
                SecondLastName = "Canales",
                IsActive = true,
                IsAdmin = false,
                ModifiedAt = DateTime.Now,
                Name = "Daniel",
                Password = "iccnm3vwVVtzoiVLj6eLTw=="
            };

            await unitOfWork.UserRepository.AddAsync(fakeUser, null);
            await unitOfWork.SaveChangesAsync();

            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeSignUp = new SignUpRequest()
            {
                Email = "jRodriguez@gmail.com",
                Password = "123456",
                FirstLastName = "Del Piero",
                SecondLastName = "Rodriguez"
            };

            var result = await loginService.SignUp(fakeSignUp);
            Assert.False(result.Valid);
            Assert.Null(result.Data);
            Assert.Equal(4, result.ErrorCode);
        }

        [Fact]
        public async void TestSignUpUserCreated()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg =>
            {
                cfg.
                CreateMap<User, LoginResponse>()
                .ForMember(u => u.Id, l => l.MapFrom(u => u.Id))
                .ForMember(u => u.Name, l => l.MapFrom(u => u.Name))
                .ForMember(u => u.FirstLastName, l => l.MapFrom(u => u.FirstLastName))
                .ForMember(u => u.SecondLastName, l => l.MapFrom(u => u.SecondLastName))
                .ForMember(u => u.Direction, l => l.MapFrom(u => u.Direction))
                .ForMember(u => u.Dni, l => l.MapFrom(u => u.Dni))
                .ForMember(u => u.Email, l => l.MapFrom(u => u.Email))
                .ForMember(u => u.Password, l => l.MapFrom(u => Encript.DesencriptText(u.Password)));
                cfg.
                CreateMap<SignUpRequest, User>()
                .ForMember(u => u.Email, u => u.MapFrom(s => s.Email))
                .ForMember(u => u.Password, u => u.MapFrom(s => Encript.EncriptText(s.Password)))
                .ForMember(u => u.Name, u => u.MapFrom(s => s.Name))
                .ForMember(u => u.FirstLastName, u => u.MapFrom(s => s.FirstLastName))
                .ForMember(u => u.SecondLastName, u => u.MapFrom(s => s.SecondLastName));
            });

            JwtOptions jwtOptions = new JwtOptions()
            {
                Audience = "reader",
                Issuer = "issuer",
                SecretKey = "fqweqw1234232sfwqfcdfsdg342352265gsdg"
            };

            IOptions<JwtOptions> optionsSecurity = Options.Create(jwtOptions);
            IMapper mapper = new Mapper(config);

            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var jwtFactory = new JWTFactory(optionsSecurity);
            var loginService = new LoginService(jwtFactory, unitOfWork, mapper);

            var fakeSignUp = new SignUpRequest()
            {
                Email = "pedro@gmail.com",
                Password = "123456",
                Name = "Pedro",
                FirstLastName = "Del Piero",
                SecondLastName = "Rodriguez"
            };

            var result = await loginService.SignUp(fakeSignUp);
            Assert.True(result.Valid);
            Assert.NotNull(result.Data);
        }
    }
}
