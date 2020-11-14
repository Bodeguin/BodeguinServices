
using AutoMapper;
using Bodeguin.Application.Communication.Request;
using Bodeguin.Application.Service;
using Bodeguin.Domain.Entity;
using Bodeguin.Infraestructure.Context;
using Bodeguin.Infraestructure.Repository;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;

namespace XUnitTest
{
    public class PurchaseUnitTest
    {
        [Fact]
        public async void TestSaveShopCartSuccess()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString())
                .UseQueryTrackingBehavior(QueryTrackingBehavior.NoTracking)
                .Options;

            var config = new MapperConfiguration(cfg =>
            {
                cfg.
                CreateMap<DetailRequest, Detail>()
                .ForMember(d => d.InventoryId, d => d.MapFrom(dr => dr.InventoryId))
                .ForMember(d => d.Quantity, d => d.MapFrom(dr => dr.Quantity))
                .ForMember(d => d.Price, d => d.MapFrom(dr => dr.Price));
                cfg.
                CreateMap<ShopCartRequest, Voucher>()
                .ForMember(v => v.PaymentTypeId, v => v.MapFrom(sr => sr.PaymentId))
                .ForMember(v => v.UserId, v => v.MapFrom(sr => sr.UserId));
            });

            IMapper mapper = new Mapper(config);
            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);

            List<DetailRequest> list = new List<DetailRequest>();
            var fakeStore = new Store
            {
                Id = 1,
                Description = "Decription",
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                Direction = "Direction",
                IsActive = true,
                Latitude = 12.459874,
                Longitude = -76.514897,
                Name = "Tiendita",
                Ruc = "10541236549"
            };
            var fakeProduct = new Product
            {
                Name = "Mariscos",
                Description = "Descripcion",
                CategoryId = 2,
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                IsActive = true,
                UrlImage = "https://wwww.google.com"
            };
            var fakeInventory = new Inventory
            {
                Id = 25,
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                IsActive = true,
                MeasureUnit = 1,
                Price = 12.5f,
                ProductId = 1,
                Quantity = 10,
                StoreId = 1,
                Product = fakeProduct,
                Store = fakeStore
            };
            var fakeDetailRequest = new DetailRequest
            {
                Price = 12.20f,
                Quantity = 1,
                InventoryId = 25
            };
            list.Add(fakeDetailRequest);
            var fakeShopRequest = new ShopCartRequest
            {
                PaymentId = 1,
                UserId = 1,
                Detail = list
            };

            await unitOfWork.ProductRepository.AddAsync(fakeProduct, null);
            await unitOfWork.InventoryRepository.AddAsync(fakeInventory, null);
            await unitOfWork.SaveChangesAsync();

            var context2 = new AppDbContext(options);
            var unitOfWork2 = new UnitOfWork(context2);
            var purchaseService = new PurchaseService(unitOfWork2, mapper);
            var result = await purchaseService.SaveShopCart(fakeShopRequest);
            Assert.True(result.Valid);
        }
    }
}
