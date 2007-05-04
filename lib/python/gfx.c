/* gfx.c

   Python wrapper for gfx convert

   Part of the swftools package.

   Copyright (c) 2003 Matthias Kramm <kramm@quiss.org>
 
   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA */

#include <Python.h>
#undef HAVE_STAT
#include "../devices/swf.h"
#include "../devices/render.h"
#include "../devices/rescale.h"
#include "../devices/text.h"
#include "../pdf/pdf.h"
#include "../log.h"
#include "../utf8.h"

gfxsource_t*pdfdriver;

staticforward PyTypeObject OutputClass;
staticforward PyTypeObject PageClass;
staticforward PyTypeObject DriverClass;

typedef struct {
    PyObject_HEAD
    gfxdevice_t*output_device;
} OutputObject;

typedef struct {
    PyObject_HEAD
    PyObject*parent;
    gfxpage_t*page;
    int nr;
} PageObject;

typedef struct {
    PyObject_HEAD
    gfxdocument_t*doc;
    char*filename;
} DocObject;

static char* strf(char*format, ...)
{
    char buf[1024];
    int l;
    va_list arglist;
    va_start(arglist, format);
    vsprintf(buf, format, arglist);
    va_end(arglist);
    return strdup(buf);
}
#define PY_ERROR(s,args...) (PyErr_SetString(PyExc_Exception, strf(s, ## args)),NULL)
#define PY_NONE Py_BuildValue("s", 0)

//---------------------------------------------------------------------
staticforward PyObject* output_save(PyObject* _self, PyObject* args, PyObject* kwargs);
staticforward PyObject* output_startframe(PyObject* _self, PyObject* args, PyObject* kwargs);
staticforward PyObject* output_endframe(PyObject* _self, PyObject* args, PyObject* kwargs);

static PyMethodDef output_methods[] =
{
    /* Output functions */
    {"save", (PyCFunction)output_save, METH_KEYWORDS, ""},
    {"startframe", (PyCFunction)output_startframe, METH_KEYWORDS, ""},
    {"endframe", (PyCFunction)output_endframe, METH_KEYWORDS, ""},
    {0,0,0,0}
};
static PyObject* output_save(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    OutputObject* self = (OutputObject*)_self;
    char*filename = 0;
    static char *kwlist[] = {"filename", NULL};
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "s", kwlist, &filename))
	return NULL;

    gfxresult_t*result = self->output_device->finish(self->output_device);
    self->output_device = 0;
    if(result->save(result, filename) < 0) {
	return PY_ERROR("Couldn't write to %s", filename);
    }
    result->destroy(result);
    return PY_NONE;
}
static PyObject* output_startframe(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    OutputObject* self = (OutputObject*)_self;
    int width=0, height=0;
    if (!PyArg_ParseTuple(args, "ii", &width, &height))
	return NULL;
    self->output_device->startpage(self->output_device, width, height);
    return PY_NONE;
}
static PyObject* output_endframe(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    OutputObject* self = (OutputObject*)_self;
    if (!PyArg_ParseTuple(args, ""))
	return NULL;
    self->output_device->endpage(self->output_device);
    return PY_NONE;
}
static PyObject* f_createSWF(PyObject* parent, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {NULL};
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "", kwlist))
	return NULL;
    OutputObject*self = PyObject_New(OutputObject, &OutputClass);
    
    self->output_device = malloc(sizeof(gfxdevice_t));
    gfxdevice_swf_init(self->output_device);
    return (PyObject*)self;
}
static PyObject* f_createImageList(PyObject* parent, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {NULL};
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "", kwlist))
	return NULL;
    OutputObject*self = PyObject_New(OutputObject, &OutputClass);
    
    self->output_device = malloc(sizeof(gfxdevice_t));
    gfxdevice_render_init(self->output_device);
    return (PyObject*)self;
}
static PyObject* f_createPlainText(PyObject* parent, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {NULL};
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "", kwlist))
	return NULL;
    OutputObject*self = PyObject_New(OutputObject, &OutputClass);
    
    self->output_device = malloc(sizeof(gfxdevice_t));
    gfxdevice_text_init(self->output_device);
    return (PyObject*)self;
}


static void output_dealloc(PyObject* _self) {
    OutputObject* self = (OutputObject*)_self;

    if(self->output_device) {
        gfxresult_t*result = self->output_device->finish(self->output_device);
	result->destroy(result);result=0;
        self->output_device = 0;
    }
    
    PyObject_Del(self);
}
static PyObject* output_getattr(PyObject * _self, char* a)
{
    OutputObject*self = (OutputObject*)_self;
    
/*    if(!strcmp(a, "x1")) {
        return PyInt_FromLong(self->output_device->x1);
    } else if(!strcmp(a, "y1")) {
        return PyInt_FromLong(self->output_device->y1);
    } else if(!strcmp(a, "x2")) {
        return PyInt_FromLong(self->output_device->x2);
    } else if(!strcmp(a, "y2")) {
        return PyInt_FromLong(self->output_device->y2);
    }*/
    
    return Py_FindMethod(output_methods, _self, a);
}
static int output_setattr(PyObject * _self, char* a, PyObject * o) 
{
    OutputObject*self = (OutputObject*)_self;
    if(!PyString_Check(o))
        return -1;
    char*value = PyString_AsString(o);
    self->output_device->setparameter(self->output_device, a, value);
    return -1;
}
static int output_print(PyObject * _self, FILE *fi, int flags)
{
    OutputObject*self = (OutputObject*)_self;
    fprintf(fi, "%08x(%d)", (int)_self, _self?_self->ob_refcnt:0);
    return 0;
}

//---------------------------------------------------------------------
staticforward PyObject* page_render(PyObject* _self, PyObject* args, PyObject* kwargs);
staticforward PyObject* page_asImage(PyObject* _self, PyObject* args, PyObject* kwargs);

static PyMethodDef page_methods[] =
{
    /* Page functions */
    {"render", (PyCFunction)page_render, METH_KEYWORDS, ""},
    {"asImage", (PyCFunction)page_asImage, METH_KEYWORDS, ""},
    {0,0,0,0}
};
static PyObject* page_render(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    PageObject* self = (PageObject*)_self; 
    
    static char *kwlist[] = {"dev", "move", "clip", NULL};
    OutputObject*output = 0;
    PyObject*move=0;
    PyObject*clip=0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "O!|OO", kwlist, &OutputClass, &output,
                &move,&clip
                ))
	return NULL;

    int x=0,y=0;
    int cx1=0,cy1=0,cx2=0,cy2=0;

    if(move) {
	if (!PyArg_ParseTuple(move, "ii", &x,&y))
	    return NULL;
    }
    if(clip) {
	if (!PyArg_ParseTuple(clip, "iiii", &cx1,&cy1,&cx2,&cy2))
	    return NULL;
    }

    if(x|y|cx1|cx2|cy1|cy2)
        self->page->rendersection(self->page, output->output_device,x,y,cx1,cy1,cx2,cy2);
    else
        self->page->render(self->page, output->output_device);
    return PY_NONE;
}

static PyObject* page_asImage(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    PageObject* self = (PageObject*)_self; 
    
    static char *kwlist[] = {"width", "height", NULL};
    int width=0,height=0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "ii", kwlist, &width, &height))
	return NULL;

    if(!width || !height) {
	return PY_ERROR("invalid dimensions: %dx%d", width,height);
    }

    gfxdevice_t dev1,dev2;
    gfxdevice_render_init(&dev1);
    dev1.setparameter(&dev1, "antialise", "2");
    gfxdevice_rescale_init(&dev2, &dev1, width, height);
    dev2.startpage(&dev2, self->page->width, self->page->height);
    self->page->render(self->page, &dev2);
    dev2.endpage(&dev2);
    gfxresult_t*result = dev2.finish(&dev2);
    gfximage_t*img = (gfximage_t*)result->get(result,"page0");
    int l = img->width*img->height;
    unsigned char*data = malloc(img->width*img->height*3);
    int s,t;
    for(t=0,s=0;t<l;s+=3,t++) {
	data[s+0] = img->data[t].r;
	data[s+1] = img->data[t].g;
	data[s+2] = img->data[t].b;
    }
    result->destroy(result);
    return PyString_FromStringAndSize((char*)data,img->width*img->height*3);
}

static void page_dealloc(PyObject* _self) {
    PageObject* self = (PageObject*)_self; 
    if(self->page) {
        self->page->destroy(self->page);
        self->page=0;
    }
    if(self->parent) {
	Py_DECREF(self->parent);
	self->parent=0;
    }
    
    PyObject_Del(self);
}
static PyObject* page_getattr(PyObject * _self, char* a)
{
    PageObject*self = (PageObject*)_self;
    
    if(!strcmp(a, "size")) {
        return Py_BuildValue("(ii)", self->page->width, self->page->height);
    } if(!strcmp(a, "doc")) {
	Py_INCREF(self->parent);
        return self->parent;
    } if(!strcmp(a, "nr")) {
        return PyInt_FromLong(self->nr);
    } else if(!strcmp(a, "width")) {
        return PyInt_FromLong(self->page->width);
    } else if(!strcmp(a, "height")) {
        return PyInt_FromLong(self->page->height);
    }
    return Py_FindMethod(page_methods, _self, a);
}
static int page_setattr(PyObject * self, char* a, PyObject * o) {
    return -1;
}
static int page_print(PyObject * _self, FILE *fi, int flags)
{
    PageObject*self = (PageObject*)_self;
    fprintf(fi, "%08x(%d)", (int)_self, _self?_self->ob_refcnt:0);
    return 0;
}

//---------------------------------------------------------------------

staticforward PyObject* doc_getPage(PyObject* parent, PyObject* args, PyObject* kwargs);

static PyMethodDef doc_methods[] =
{
    /* PDF functions */
    {"getPage", (PyCFunction)doc_getPage, METH_KEYWORDS, ""},
    {0,0,0,0}
};

static PyObject* doc_getPage(PyObject* _self, PyObject* args, PyObject* kwargs)
{
    DocObject* self = (DocObject*)_self;

    static char *kwlist[] = {"nr", NULL};
    int pagenr = 0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "i", kwlist, &pagenr))
	return NULL;

    PageObject*page = PyObject_New(PageObject, &PageClass);
    page->page = self->doc->getpage(self->doc, pagenr);
    page->nr = pagenr;
    page->parent = _self;
    Py_INCREF(page->parent);
    if(!page->page) {
        PyObject_Del(page);
        return PY_ERROR("Couldn't extract page %d", pagenr);
    }
    return (PyObject*)page;
}

static PyObject* f_open(PyObject* parent, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {"type", "filename", NULL};
    char*filename;
    char*type;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "ss", kwlist, &type, &filename))
	return NULL;

    DocObject*self = PyObject_New(DocObject, &DriverClass);
   
    if(!strcmp(type,"pdf"))
	self->doc = pdfdriver->open(filename);
    else
	return PY_ERROR("Unknown type %s", type);

    if(!self->doc) {
        PyObject_Del(self);
        return PY_ERROR("Couldn't open %s", filename);
    }
    self->filename = strdup(filename);
    return (PyObject*)self;
}
static void doc_dealloc(PyObject* _self) {
    DocObject* self = (DocObject*)_self;
    if(self->doc) {
        self->doc->destroy(self->doc);
        self->doc=0;
    }
    if(self->filename) {
	free(self->filename);self->filename=0;
    }
    PyObject_Del(self);
}
static PyObject* doc_getattr(PyObject * _self, char* a)
{
    DocObject*self = (DocObject*)_self;
    if(!strcmp(a, "pages")) {
        return PyInt_FromLong(self->doc->num_pages);
    }
    if(!strcmp(a, "filename")) {
        return PyString_FromString(self->filename);
    }
    return Py_FindMethod(doc_methods, _self, a);
}
static int doc_setattr(PyObject * self, char* a, PyObject * o) {
    return -1;
}
static int doc_print(PyObject * _self, FILE *fi, int flags)
{
    DocObject*self = (DocObject*)_self;
    fprintf(fi, "%08x(%d)", (int)_self, _self?_self->ob_refcnt:0);
    return 0;
}

//---------------------------------------------------------------------

static PyTypeObject OutputClass =
{
    PyObject_HEAD_INIT(NULL)
    0,
    tp_name: "Output",
    tp_basicsize: sizeof(OutputObject),
    tp_itemsize: 0,
    tp_dealloc: output_dealloc,
    tp_print: output_print,
    tp_getattr: output_getattr,
    tp_setattr: output_setattr,
};
static PyTypeObject PageClass =
{
    PyObject_HEAD_INIT(NULL)
    0,
    tp_name: "Page",
    tp_basicsize: sizeof(PageObject),
    tp_itemsize: 0,
    tp_dealloc: page_dealloc,
    tp_print: page_print,
    tp_getattr: page_getattr,
    tp_setattr: page_setattr,
};
static PyTypeObject DriverClass =
{
    PyObject_HEAD_INIT(NULL)
    0,
    tp_name: "PDF",
    tp_basicsize: sizeof(DocObject),
    tp_itemsize: 0,
    tp_dealloc: doc_dealloc,
    tp_print: doc_print,
    tp_getattr: doc_getattr,
    tp_setattr: doc_setattr,
};

//=====================================================================

static PyObject* f_setoption(PyObject* self, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {"key", "value", NULL};
    char*key=0,*value=0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "ss", kwlist, &key, &value))
	return NULL;
    pdfdriver->set_parameter(key,value);
    return PY_NONE;
}

static PyObject* f_addfont(PyObject* self, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {"filename", NULL};
    char*filename=0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "s", kwlist, &filename))
	return NULL;
    pdfdriver->set_parameter("font", filename);
    return PY_NONE;
}

static PyObject* f_addfontdir(PyObject* self, PyObject* args, PyObject* kwargs)
{
    static char *kwlist[] = {"filename", NULL};
    char*filename=0;
    if (!PyArg_ParseTupleAndKeywords(args, kwargs, "s", kwlist, &filename))
	return NULL;
    pdfdriver->set_parameter("fontdir", filename);
    return PY_NONE;
}

static PyMethodDef pdf2swf_methods[] =
{
    /* sources */
    {"open", (PyCFunction)f_open, METH_KEYWORDS, ""},
    {"addfont", (PyCFunction)f_addfont, METH_KEYWORDS, ""},
    {"addfontdir", (PyCFunction)f_addfontdir, METH_KEYWORDS, ""},
    {"setoption", (PyCFunction)f_setoption, METH_KEYWORDS, ""},

    /* devices */
    {"SWF", (PyCFunction)f_createSWF, METH_KEYWORDS, ""},
    {"ImageList", (PyCFunction)f_createImageList, METH_KEYWORDS, ""},
    {"PlainText", (PyCFunction)f_createPlainText, METH_KEYWORDS, ""},

    /* sentinel */
    {0, 0, 0, 0}
};

void initgfx(void)
{
    initLog(0,0,0,0,0,2);
    OutputClass.ob_type = &PyType_Type;
    PageClass.ob_type = &PyType_Type;
    DriverClass.ob_type = &PyType_Type;
 
    pdfdriver = gfxsource_pdf_create();
    
    PyObject*module = Py_InitModule("gfx", pdf2swf_methods);
}