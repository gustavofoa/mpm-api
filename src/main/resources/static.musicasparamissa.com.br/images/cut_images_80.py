from pil import Image, ImageOps, ImageDraw
import glob, os

size = 120, 100

for infile in glob.glob("diasLiturgicos/*.*"):
    name = os.path.basename(infile)
    file, ext = os.path.splitext(infile)
    im = Image.open(infile)

    output = ImageOps.fit(im, size, centering=(0.5, 0.5))
        
    output.save("diasLiturgicos/80x80/" + name)
