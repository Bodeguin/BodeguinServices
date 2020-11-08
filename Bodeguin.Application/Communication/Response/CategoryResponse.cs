using System;
using System.Collections.Generic;
using System.Text;

namespace Bodeguin.Application.Communication.Response
{
    public class CategoryResponse
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string UrlImage { get; set; }
        public int NumProducts { get; set; }
    }
}
