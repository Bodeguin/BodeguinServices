using Bodeguin.Domain.Entity;
using Bodeguin.Domain.Interface;
using Bodeguin.Infraestructure.Context;

namespace Bodeguin.Infraestructure.Repository
{
    public class InventoryRepository : GenericRepository<Inventory, int>, IInventoryRepository
    {
        public InventoryRepository(AppDbContext context) : base(context) { } 
    }
}
